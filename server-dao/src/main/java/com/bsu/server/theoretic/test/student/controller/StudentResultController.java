package com.bsu.server.theoretic.test.student.controller;

import com.bsu.server.theoretic.test.student.dto.StudentResultDto;
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
    public void saveResult(StudentResultDto result) {
        em.persist(result);
        em.flush();
    }

    @Transactional(readOnly = true)
    public List<StudentResultDto> getStudentResults(Integer userId) {
        return em.createQuery("from StudentResultDto where student.id = :studentId", StudentResultDto.class)
                .setParameter("studentId", userId).getResultList();
    }
}
