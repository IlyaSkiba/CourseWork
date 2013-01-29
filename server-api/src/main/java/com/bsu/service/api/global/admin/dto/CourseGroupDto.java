package com.bsu.service.api.global.admin.dto;

/**
 * @author HomeUser
 *         Date: 27.1.13
 *         Time: 13.55
 */
public class CourseGroupDto {
    private UserDto groupOwner;
    private UserGroupDto group;

    public UserDto getGroupOwner() {
        return groupOwner;
    }

    public void setGroupOwner(UserDto groupOwner) {
        this.groupOwner = groupOwner;
    }

    public UserGroupDto getGroup() {
        return group;
    }

    public void setGroup(UserGroupDto group) {
        this.group = group;
    }
}
