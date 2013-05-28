package com.bsu.server.theoretic.test.student.controller;

import com.bsu.server.controller.UserController;
import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.security.UserAccount;
import com.bsu.server.theoretic.test.controller.QuestionController;
import com.bsu.server.theoretic.test.dto.QuestionEntity;
import com.bsu.server.theoretic.test.student.entity.QStudentAnswerEntity;
import com.bsu.server.theoretic.test.student.entity.StudentAnswerEntity;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ilya Skiba
 */
@Service
@Transactional(readOnly = false)
public class StudentAnswerController extends BaseController<StudentAnswerEntity> {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserController userController;
    @Autowired
    private QuestionController questionController;

    @Transactional(readOnly = false)
    public List<StudentAnswerEntity> getAnswers(Integer questionId, Integer studentId) {
        JPQLQuery query = new JPAQuery(em);
        return query.from(QStudentAnswerEntity.studentAnswerEntity)
                .where(QStudentAnswerEntity.studentAnswerEntity.question.id.eq(questionId)
                        .and(QStudentAnswerEntity.studentAnswerEntity.student.id.eq(studentId)))
                .list(QStudentAnswerEntity.studentAnswerEntity);
    }

    public void saveResults(List<StudentAnswerEntity> answers, Integer studentId) {
        UserAccount user = userController.getById(studentId);
        List<StudentAnswerEntity> results = new ArrayList<>(answers);
        for (StudentAnswerEntity currAnswer : results) {
            currAnswer.setStudent(user);
            em.persist(currAnswer);
        }
        em.flush();
    }

    public void cleanupTestResults(Integer studentId, Integer testId) {
        List<QuestionEntity> questions = questionController.getQuestionsForTest(testId);
        if (questions == null) {
            return;
        }
        for (QuestionEntity question : questions) {
            List<StudentAnswerEntity> answers = getAnswers(question.getId(), studentId);
            if (answers == null) {
                continue;
            }
            for (StudentAnswerEntity answerDto : answers) {
                em.remove(answerDto);
            }
        }
        em.flush();
    }

    @Override
    protected Class<StudentAnswerEntity> getEntityClass() {
        return StudentAnswerEntity.class;
    }
}
