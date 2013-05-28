package com.bsu.server.theoretic.test.dto;

import com.bsu.server.dto.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Ilya Skiba
 */
@Entity
@Table(name = "question_answer", schema = "public")
public class AnswerEntity extends BaseEntity implements Serializable {

    @Column(name = "answer")
    private String textAnswer;

    @Column(name = "is_right")
    private Boolean isRight;

    @ManyToOne
    private QuestionEntity questionEntity;

    public String getTextAnswer() {
        return textAnswer;
    }

    public void setTextAnswer(String textAnswer) {
        this.textAnswer = textAnswer;
    }

    public Boolean getRight() {
        return isRight;
    }

    public void setRight(Boolean right) {
        isRight = right;
    }

    public QuestionEntity getQuestionEntity() {
        return questionEntity;
    }

    public void setQuestionEntity(QuestionEntity questionEntity) {
        this.questionEntity = questionEntity;
    }
}
