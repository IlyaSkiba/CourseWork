package com.bsu.server.dto;

import com.bsu.server.dto.security.UserAccount;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Ilya Skiba
 */
@Entity
@Table(name = "user_group", schema = "public")
public class UserGroupEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "group_name")
    private String groupName;

    @ManyToMany
    private List<UserAccount> assignedUsers;

    @ManyToMany
    private List<CourseGroupEntity> courses;

    public Integer getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<CourseGroupEntity> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseGroupEntity> courses) {
        this.courses = courses;
    }

    public List<UserAccount> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<UserAccount> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }
}
