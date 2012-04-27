package com.bsu.server.theoretic.test.controller;

import com.bsu.server.theoretic.test.dto.AnswerDto;
import com.bsu.server.theoretic.test.dto.QuestionDto;
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
public class QuestionController {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = false)
    public List<Integer> getQuestionIds(Integer testId) {
        TypedQuery<Integer> query = em.createQuery("select id from QuestionDto where test.id = :themeId", Integer.class);
        query.setParameter("themeId", testId);
        return query.getResultList();
    }

    @Transactional(readOnly = false)
    public List<QuestionDto> getQuestionsForTest(Integer testId) {
        TypedQuery<QuestionDto> query = em.createQuery("from QuestionDto where test.id = :themeId", QuestionDto.class);
        query.setParameter("themeId", testId);
        return query.getResultList();
    }

    @Transactional(readOnly = false)
    public QuestionDto getQuestionDto(Integer questionId) {
        TypedQuery<QuestionDto> query = em.createQuery("from QuestionDto where id = :questionId", QuestionDto.class);
        query.setParameter("questionId", questionId);
        return query.getSingleResult();
    }

    @Transactional(readOnly = false)
    public List<AnswerDto> getRightAnswers(Integer questionId) {
        TypedQuery<AnswerDto> query = em.createQuery("from AnswerDto where questionDto.id = :questionId and isRight=:true",
                AnswerDto.class);
        query.setParameter("questionId", questionId);
        query.setParameter("true", true);
        return query.getResultList();
    }

    @Transactional(readOnly = false)
    public List<AnswerDto> getAnswers(Integer questionId) {
        TypedQuery<AnswerDto> query = em.createQuery("from AnswerDto where questionDto.id = :questionId",
                AnswerDto.class);
        query.setParameter("questionId", questionId);
        return query.getResultList();
    }
}
