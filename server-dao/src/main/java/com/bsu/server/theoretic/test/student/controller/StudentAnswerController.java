package com.bsu.server.theoretic.test.student.controller;

import com.bsu.server.controller.UserController;
import com.bsu.server.dto.security.UserAccount;
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
    public List<StudentAnswerDto> getAnswers(Integer questionId) {
        TypedQuery<StudentAnswerDto> query = em.createQuery("from StudentAnswerDto where question.id = :questionId",
                StudentAnswerDto.class);
        query.setParameter("questionId", questionId);
        return query.getResultList();
    }

    @Transactional(readOnly = false)
    public void saveResults(List<StudentAnswerDto> answers, Integer id) {
        UserAccount user = userController.getUser(id);
        List<StudentAnswerDto> results = new ArrayList<StudentAnswerDto>(answers);
        for (StudentAnswerDto currAnswer : results) {
            currAnswer.setStudent(user);
            em.persist(currAnswer);
        }
        em.flush();
    }
}
