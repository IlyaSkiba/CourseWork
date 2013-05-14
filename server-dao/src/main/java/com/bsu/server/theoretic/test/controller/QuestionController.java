package com.bsu.server.theoretic.test.controller;

import com.bsu.server.controller.common.BaseController;
import com.bsu.server.theoretic.test.dto.AnswerEntity;
import com.bsu.server.theoretic.test.dto.QuestionEntity;
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
public class QuestionController extends BaseController<QuestionEntity> {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = false)
    public List<Integer> getQuestionIds(Integer testId) {
        TypedQuery<Integer> query = em.createQuery("select id from QuestionEntity where test.id = :themeId", Integer.class);
        query.setParameter("themeId", testId);
        return query.getResultList();
    }

    @Transactional(readOnly = false)
    public List<QuestionEntity> getQuestionsForTest(Integer testId) {
        TypedQuery<QuestionEntity> query = em.createQuery("from QuestionEntity where test.id = :themeId", QuestionEntity.class);
        query.setParameter("themeId", testId);
        return query.getResultList();
    }

    @Transactional(readOnly = false)
    public QuestionEntity getQuestionDto(Integer questionId) {
        TypedQuery<QuestionEntity> query = em.createQuery("from QuestionEntity where id = :questionId", QuestionEntity.class);
        query.setParameter("questionId", questionId);
        return query.getSingleResult();
    }

    @Transactional(readOnly = false)
    public List<AnswerEntity> getRightAnswers(Integer questionId) {
        TypedQuery<AnswerEntity> query = em.createQuery("from AnswerEntity where questionDto.id = :questionId and isRight=:true",
                AnswerEntity.class);
        query.setParameter("questionId", questionId);
        query.setParameter("true", true);
        return query.getResultList();
    }

    @Transactional(readOnly = false)
    public List<AnswerEntity> getAnswers(Integer questionId) {
        TypedQuery<AnswerEntity> query = em.createQuery("from AnswerEntity where questionDto.id = :questionId",
                AnswerEntity.class);
        query.setParameter("questionId", questionId);
        return query.getResultList();
    }

    @Override
    protected Class<QuestionEntity> getEntityClass() {
        return QuestionEntity.class;
    }
}
