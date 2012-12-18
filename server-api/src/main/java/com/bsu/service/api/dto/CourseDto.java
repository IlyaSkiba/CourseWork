package com.bsu.service.api.dto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: meloman
 * Date: 08.10.12
 * Time: 12:06
 */
public class CourseDto {
    private Integer id;
    private String courseName;
    private List<Integer> themeIds;
    private Integer ownerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<Integer> getThemeIds() {
        return themeIds;
    }

    public void setThemeIds(List<Integer> themeIds) {
        this.themeIds = themeIds;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }
}
