package com.bsu.server.theoretic.test.student.dto;

import com.bsu.server.dto.security.UserAccount;
import com.bsu.server.theoretic.test.dto.QuestionDto;

import javax.persistence.*;

/**
 * @author Ilya Skiba
 */
@Entity
@Table(name = "student_answer", schema = "public")
public class StudentAnswerDto {

    @Column(name = "id")
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "answer_text")
    private String answerText;

    @ManyToOne
    private UserAccount student;

    @ManyToOne
    private QuestionDto question;

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

    public QuestionDto getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDto question) {
        this.question = question;
    }
}
