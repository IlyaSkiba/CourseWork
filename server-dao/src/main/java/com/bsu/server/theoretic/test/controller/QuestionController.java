package com.bsu.server.theoretic.test.controller;

import com.bsu.server.theoretic.test.dto.QuestionDto;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Ilya Skiba
 */
@Service
public class QuestionController {

    @PersistenceContext
    private EntityManager em;

    public List<Integer> getQuestionIds(Integer testId) {
        TypedQuery<Integer> query = em.createQuery("select id from QuestionDto where test.id = :themeId", Integer.class);
        query.setParameter("themeId", testId);
        return query.getResultList();
    }

    public List<QuestionDto> getQuestionsForTest(Integer testId) {
        TypedQuery<QuestionDto> query = em.createQuery("from QuestionDto where test.id = :themeId", QuestionDto.class);
        query.setParameter("themeId", testId);
        return query.getResultList();
    }

    public QuestionDto getQuestionDto(Integer questionId) {
        TypedQuery<QuestionDto> query = em.createQuery("from QuestionDto where id = :questionId", QuestionDto.class);
        query.setParameter("questionId", questionId);
        return query.getSingleResult();
    }
}
