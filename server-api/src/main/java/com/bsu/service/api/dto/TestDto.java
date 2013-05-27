package com.bsu.service.api.dto;

import com.bsu.service.api.dto.base.BaseDto;

/**
 * @author HomeUser
 *         Date: 27.5.13
 *         Time: 23.07
 */
public class TestDto extends BaseDto {
    private Integer weight;
    private Integer themeId;
    private Integer questionCount;

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }
}
