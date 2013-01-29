package com.bsu.server.dto;

import com.bsu.server.dto.security.UserAccount;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author HomeUser
 *         Date: 15.1.13
 *         Time: 23.56
 */
@Entity
@Table(name = "course_group", schema = "public")
public class CourseGroupEntity implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @ManyToMany(mappedBy = "assignedGroups")
    public List<ThemeEntity> availableThemes;

    @ManyToOne
    private CourseEntity assignedCourse;

    @ManyToOne
    private UserAccount groupOwner;

    public List<ThemeEntity> getAvailableThemes() {
        return availableThemes;
    }

    public void setAvailableThemes(List<ThemeEntity> availableThemes) {
        this.availableThemes = availableThemes;
    }

    public Integer getId() {
        return id;
    }

    public CourseEntity getAssignedCourse() {
        return assignedCourse;
    }

    public void setAssignedCourse(CourseEntity assignedCourse) {
        this.assignedCourse = assignedCourse;
    }

    public UserAccount getGroupOwner() {
        return groupOwner;
    }

    public void setGroupOwner(UserAccount groupOwner) {
        this.groupOwner = groupOwner;
    }
}
