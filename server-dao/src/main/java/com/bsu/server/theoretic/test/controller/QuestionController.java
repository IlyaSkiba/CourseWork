package com.bsu.server.theoretic.test.controller;

import com.bsu.server.controller.common.BaseController;
import com.bsu.server.theoretic.test.dto.AnswerEntity;
import com.bsu.server.theoretic.test.dto.QAnswerEntity;
import com.bsu.server.theoretic.test.dto.QQuestionEntity;
import com.bsu.server.theoretic.test.dto.QuestionEntity;
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
public class QuestionController extends BaseController<QuestionEntity> {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = false)
    public List<QuestionEntity> getQuestionsForTest(Integer testId) {
        JPQLQuery query = new JPAQuery(em);
        query = query.from(QQuestionEntity.questionEntity).where(QQuestionEntity.questionEntity.test.id.eq(testId));
        return query.list(QQuestionEntity.questionEntity);
    }

    @Transactional(readOnly = false)
    public QuestionEntity getQuestionDto(Integer questionId) {
        JPQLQuery query = new JPAQuery(em);
        query = query.from(QQuestionEntity.questionEntity).where(QQuestionEntity.questionEntity.id.eq(questionId));
        return query.singleResult(QQuestionEntity.questionEntity);
    }

    @Transactional(readOnly = false)
    public List<AnswerEntity> getRightAnswers(Integer questionId) {
        JPQLQuery query = new JPAQuery(em);
        query = query.from(QAnswerEntity.answerEntity).where(QAnswerEntity.answerEntity.questionEntity.id.eq(questionId)
                .and(QAnswerEntity.answerEntity.isRight.eq(true)));
        return query.list(QAnswerEntity.answerEntity);
    }

    @Transactional(readOnly = false)
    public List<AnswerEntity> getAnswers(Integer questionId) {
        JPQLQuery query = new JPAQuery(em);
        query = query.from(QAnswerEntity.answerEntity).where(QAnswerEntity.answerEntity.questionEntity.id.eq(questionId));
        return query.list(QAnswerEntity.answerEntity);
    }

    @Override
    protected Class<QuestionEntity> getEntityClass() {
        return QuestionEntity.class;
    }
}
