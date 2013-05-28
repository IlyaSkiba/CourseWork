package com.bsu.service.api.dto;

/**
 * Created with IntelliJ IDEA.
 * User: meloman
 * Date: 02.09.12
 * Time: 16:56
 * To change this template use File | Settings | File Templates.
 */
public class StudentResultDto {
    private String userName;
    private String testName;
    private Integer resultScore;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Integer getResultScore() {
        return resultScore;
    }

    public void setResultScore(Integer resultScore) {
        this.resultScore = resultScore;
    }
}
