package com.bsu.server.theoretic.test.dto;

import com.bsu.server.dto.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Ilya Skiba
 */
@Entity
@Table(name = "question", schema = "public")
public class QuestionEntity extends BaseEntity implements Serializable {

    @Column(name = "content")
    private String question;
    @Column(name = "question_type")
    private Integer questionType;
    @OneToMany(mappedBy = "questionEntity")
    private List<AnswerEntity> answers;
    @Column(name = "weight")
    private Integer weight;
    @ManyToOne
    private TestEntity test;

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public TestEntity getTest() {
        return test;
    }

    public void setTest(TestEntity test) {
        this.test = test;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public List<AnswerEntity> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerEntity> answers) {
        this.answers = answers;
    }
}
