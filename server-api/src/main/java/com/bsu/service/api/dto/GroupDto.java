package com.bsu.service.api.dto;

import java.util.List;

/**
 * @author HomeUser
 *         Date: 15.1.13
 *         Time: 23.39
 */
public class GroupDto {
    private Integer id;
    private String groupName;
    private List<Integer> assignedUsers;

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

    public List<Integer> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<Integer> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }
}
