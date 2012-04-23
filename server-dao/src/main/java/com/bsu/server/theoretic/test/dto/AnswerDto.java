package com.bsu.server.theoretic.test.dto;

import javax.persistence.*;

/**
 * @author Ilya Skiba
 */
@Entity
@Table(name = "question_answer", schema = "public")
public class AnswerDto {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "answer")
    private String textAnswer;

    @Column(name = "is_right")
    private Boolean isRight;

    @ManyToOne
    private QuestionDto questionDto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
}
