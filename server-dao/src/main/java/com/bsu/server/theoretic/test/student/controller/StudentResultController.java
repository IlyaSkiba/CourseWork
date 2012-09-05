package com.bsu.server.theoretic.test.student.controller;

import com.bsu.server.theoretic.test.student.dto.StudentResultEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Ilya Skiba
 */
@Service
public class StudentResultController {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = false)
    public void saveResult(StudentResultEntity result) {
        em.persist(result);
        em.flush();
    }

    @Transactional(readOnly = true)
    public List<StudentResultEntity> getStudentResults(Integer userId) {
        return em.createQuery("from StudentResultEntity where student.id=:studentId", StudentResultEntity.class)
                .setParameter("studentId", userId).getResultList();
    }

    @Transactional(readOnly = true)
    public StudentResultEntity getStudentResult(Integer userId, Integer testId) {
        return em.createQuery("from StudentResultEntity where student.id=:studentId and testDto.id=:testId",
                StudentResultEntity.class)
                .setParameter("studentId", userId)
                .setParameter("testId", testId).getResultList().get(0);
    }
}
