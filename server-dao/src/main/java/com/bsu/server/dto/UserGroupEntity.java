package com.bsu.server.dto;

import com.bsu.server.dto.security.UserAccount;
import com.google.common.collect.Lists;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * @author Ilya Skiba
 */
@Entity
@Table(name = "user_group", schema = "public")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class UserGroupEntity extends BaseEntity implements Serializable {

    @Column(name = "group_name")
    private String groupName;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<UserAccount> assignedUsers = Lists.newArrayList();

    @OneToMany(orphanRemoval = true, mappedBy = "group", cascade = CascadeType.ALL)
    private List<CourseGroupEntity> courses = Lists.newArrayList();

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
        this.courses.clear();
        this.courses.addAll(courses);
    }

    public List<UserAccount> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(List<UserAccount> assignedUsers) {
        this.assignedUsers.clear();
        this.assignedUsers.addAll(assignedUsers);
    }
}
