package com.bsu.server.dto;

import com.bsu.server.dto.security.UserAccount;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * User: HomeUser
 * Date: 15.4.12
 * Time: 9.49
 */
@Entity
@Table(name = "theme", schema = "public")
public class ThemeEntity implements Serializable {
    @Id
    @Column(name = "theme_id")
    private Integer id;
    @OneToOne
    private UserAccount creator;
    @Column(name = "theme_name")
    private String name;
    @ManyToOne
    private CourseEntity courseEntity;
    @ManyToMany
    @JoinTable(name = "group_theme_list",
            joinColumns =
            @JoinColumn(name = "theme_id", referencedColumnName = "theme_id"),
            inverseJoinColumns =
            @JoinColumn(name = "group_id", referencedColumnName = "id")
    )
    private List<UserGroupDto> assignedGroups;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CourseEntity getCourseEntity() {
        return courseEntity;
    }

    public void setCourseEntity(CourseEntity courseEntity) {
        this.courseEntity = courseEntity;
    }

    public UserAccount getCreator() {
        return creator;
    }

    public void setCreator(UserAccount creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserGroupDto> getAssignedGroups() {
        return assignedGroups;
    }

    public void setAssignedGroups(List<UserGroupDto> assignedGroups) {
        this.assignedGroups = assignedGroups;
    }
}