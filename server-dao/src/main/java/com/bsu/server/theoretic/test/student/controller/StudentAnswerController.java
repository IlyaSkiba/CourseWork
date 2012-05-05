package com.bsu.server.theoretic.test.student.controller;

import com.bsu.server.controller.UserController;
import com.bsu.server.dto.security.UserAccount;
import com.bsu.server.theoretic.test.dto.QuestionDto;
import com.bsu.server.theoretic.test.student.dto.StudentAnswerDto;
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
    public List<StudentAnswerDto> getAnswers(Integer questionId, Integer studentId) {
        TypedQuery<StudentAnswerDto> query = em.createQuery("from StudentAnswerDto where question.id = :questionId and student.id = :studentId",
                StudentAnswerDto.class);
        query.setParameter("questionId", questionId);
        query.setParameter("studentId", studentId);
        return query.getResultList();
    }

    @Transactional(readOnly = false)
    public void saveResults(List<StudentAnswerDto> answers, Integer studentId) {
        UserAccount user = userController.getUser(studentId);
        List<StudentAnswerDto> results = new ArrayList<StudentAnswerDto>(answers);
        for (StudentAnswerDto currAnswer : results) {
            currAnswer.setStudent(user);
            em.persist(currAnswer);
        }
        em.flush();
    }

    @Transactional(readOnly = false)
    public void cleanupTestResults(Integer studentId, Integer testId) {
        TypedQuery<QuestionDto> query = em.createQuery("from QuestionDto where test.id=:testId", QuestionDto.class);
        query.setParameter("testId", testId);
        List<QuestionDto> questions = query.getResultList();
        if (questions == null) return;
        for (QuestionDto question : questions) {
            List<StudentAnswerDto> answers = getAnswers(question.getId(), studentId);
            if (answers == null) continue;
            for (StudentAnswerDto answerDto : answers) {
                em.remove(answerDto);
            }
        }
        em.flush();
    }
}
