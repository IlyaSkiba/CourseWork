package com.bsu.server.theoretic.test.student.controller;

import com.bsu.server.theoretic.test.student.entity.QStudentResultEntity;
import com.bsu.server.theoretic.test.student.entity.StudentResultEntity;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
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
        JPQLQuery query = new JPAQuery(em);
        return query.from(QStudentResultEntity.studentResultEntity).where(QStudentResultEntity.studentResultEntity
                .student.id.eq(userId)).list(QStudentResultEntity.studentResultEntity);
    }

    @Transactional(readOnly = true)
    public StudentResultEntity getStudentResult(Integer userId, Integer testId) {
        JPQLQuery query = new JPAQuery(em);
        return query.from(QStudentResultEntity.studentResultEntity).where(QStudentResultEntity.studentResultEntity
                .student.id.eq(userId).and(QStudentResultEntity.studentResultEntity.testEntity.id.eq(testId)))
                .singleResult(QStudentResultEntity.studentResultEntity);
    }
}
