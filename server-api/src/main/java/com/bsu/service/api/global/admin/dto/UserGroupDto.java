package com.bsu.service.api.global.admin.dto;

import java.util.List;

/**
 * @author HomeUser
 *         Date: 27.1.13
 *         Time: 14.02
 */
public class UserGroupDto {

    private Integer id;
    private String groupName;
    private List<Integer> assignedUserIds;
    private List<Integer> assignedCourseIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Integer> getAssignedUserIds() {
        return assignedUserIds;
    }

    public void setAssignedUserIds(List<Integer> assignedUserIds) {
        this.assignedUserIds = assignedUserIds;
    }

    public List<Integer> getAssignedCourseIds() {
        return assignedCourseIds;
    }

    public void setAssignedCourseIds(List<Integer> assignedCourseIds) {
        this.assignedCourseIds = assignedCourseIds;
    }
}
