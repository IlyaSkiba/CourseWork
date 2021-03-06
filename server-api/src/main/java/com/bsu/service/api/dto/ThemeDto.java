package com.bsu.service.api.dto;

import com.bsu.service.api.dto.base.BaseDto;

import java.util.List;

/**
 * @author Ilya Skiba
 */
public class ThemeDto extends BaseDto {
    private String name;
    private String creatorName;
    private List<Integer> parentThemes;
    private Integer significance;
    private Integer courseId;
    private String themeURL;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public List<Integer> getParentThemes() {
        return parentThemes;
    }

    public void setParentThemes(List<Integer> parentThemes) {
        this.parentThemes = parentThemes;
    }

    public Integer getSignificance() {
        return significance;
    }

    public void setSignificance(Integer significance) {
        this.significance = significance;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getThemeURL() {
        return themeURL;
    }

    public void setThemeURL(String themeURL) {
        this.themeURL = themeURL;
    }
}
