package com.bsu.server.theoretic.test.student.entity;

import com.bsu.server.dto.BaseEntity;
import com.bsu.server.dto.security.UserAccount;
import com.bsu.server.theoretic.test.dto.QuestionEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Ilya Skiba
 */
@Entity
@Table(name = "student_answer", schema = "public")
public class StudentAnswerEntity extends BaseEntity implements Serializable {

    @Column(name = "answer_text")
    private String answerText;

    @ManyToOne
    private UserAccount student;

    @ManyToOne
    private QuestionEntity question;

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public UserAccount getStudent() {
        return student;
    }

    public void setStudent(UserAccount student) {
        this.student = student;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }
}
