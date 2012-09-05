package com.bsu.service.api.dto;

/**
 * Created with IntelliJ IDEA.
 * User: meloman
 * Date: 02.09.12
 * Time: 16:55
 * To change this template use File | Settings | File Templates.
 */
public class AnswerDto {
    private Integer id;
    private String answer;
    private boolean isRight;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }
}
