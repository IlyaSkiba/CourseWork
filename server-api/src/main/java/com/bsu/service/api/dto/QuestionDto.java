package com.bsu.service.api.dto;

import com.bsu.service.api.dto.base.BaseDto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: meloman
 * Date: 02.09.12
 * Time: 16:51
 * To change this template use File | Settings | File Templates.
 */
public class QuestionDto extends BaseDto {
    private Integer id;
    private String question;
    private Integer openType;
    private List<AnswerDto> answerDtos;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getOpenType() {
        return openType;
    }

    public void setOpenType(Integer openType) {
        this.openType = openType;
    }

    public List<AnswerDto> getAnswerDtos() {
        return answerDtos;
    }

    public void setAnswerDtos(List<AnswerDto> answerDtos) {
        this.answerDtos = answerDtos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
