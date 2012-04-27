package com.bsu.server.theoretic.test.student.controller;

import com.bsu.server.theoretic.test.student.dto.StudentAnswerDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Ilya Skiba
 */
@Service
public class StudentAnswerController {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = false)
    public List<StudentAnswerDto> getAnswers(Integer questionId) {
        TypedQuery<StudentAnswerDto> query = em.createQuery("from StudentAnswerDto where question.id = :questionId",
                StudentAnswerDto.class);
        query.setParameter("questionId", questionId);
        return query.getResultList();
    }

    @Transactional(readOnly = false)
    public void saveResults(List<StudentAnswerDto> answers) {
        em.merge(answers.get(0));
    }
}
