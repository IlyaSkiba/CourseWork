package com.bsu.service.api.dto;

import com.bsu.service.api.dto.base.BaseDto;

/**
 * @author HomeUser
 *         Date: 29.1.13
 *         Time: 22.21
 */
public class CourseGroupDto extends BaseDto {
    private Integer courseId;
    private Integer ownerId;
    private Integer groupId;
    private String groupName;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
