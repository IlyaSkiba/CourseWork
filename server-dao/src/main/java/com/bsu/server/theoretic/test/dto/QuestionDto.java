package com.bsu.server.theoretic.test.dto;

import javax.persistence.*;
import java.util.List;

/**
 * @author Ilya Skiba
 */
@Entity
@Table(name = "question", schema = "public")
public class QuestionDto {

    @Column(name = "id")
    @Id
    private Integer id;

    @Column(name = "content")
    private String question;

    @Column(name = "question_type")
    private Integer questionType;

    @OneToMany(mappedBy = "questionDto")
    private List<AnswerDto> answers;

    @Column(name = "weight")
    private Integer weight;

    @ManyToOne
    private TestDto test;

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

    public TestDto getTest() {
        return test;
    }

    public void setTest(TestDto test) {
        this.test = test;
    }

    public Integer getId() {
        return id;
    }
}
