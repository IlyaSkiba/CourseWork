package com.bsu.server.theoretic.test.student.controller;

import com.bsu.server.controller.UserController;
import com.bsu.server.dto.security.UserAccount;
import com.bsu.server.theoretic.test.dto.QuestionEntity;
import com.bsu.server.theoretic.test.student.entity.StudentAnswerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ilya Skiba
 */
@Service
public class StudentAnswerController {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserController userController;

    @Transactional(readOnly = true)
    public List<StudentAnswerEntity> getAnswers(Integer questionId, Integer studentId) {
        TypedQuery<StudentAnswerEntity> query = em.createQuery("from StudentAnswerEntity where question.id = :questionId and student.id = :studentId",
                StudentAnswerEntity.class);
        query.setParameter("questionId", questionId);
        query.setParameter("studentId", studentId);
        return query.getResultList();
    }

    @Transactional(readOnly = false)
    public void saveResults(List<StudentAnswerEntity> answers, Integer studentId) {
        UserAccount user = userController.getUser(studentId);
        List<StudentAnswerEntity> results = new ArrayList<StudentAnswerEntity>(answers);
        for (StudentAnswerEntity currAnswer : results) {
            currAnswer.setStudent(user);
            em.persist(currAnswer);
        }
        em.flush();
    }

    @Transactional(readOnly = false)
    public void cleanupTestResults(Integer studentId, Integer testId) {
        TypedQuery<QuestionEntity> query = em.createQuery("from QuestionEntity where test.id=:testId", QuestionEntity.class);
        query.setParameter("testId", testId);
        List<QuestionEntity> questions = query.getResultList();
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
}
