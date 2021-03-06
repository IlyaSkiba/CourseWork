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
public class CourseGroupEntity extends BaseEntity implements Serializable {
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "theme_group",
            joinColumns = {@JoinColumn(name = "course_group", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "theme", referencedColumnName = "id")})
    public List<ThemeEntity> availableThemes;

    @ManyToOne
    private CourseEntity assignedCourse;

    @ManyToOne
    private UserAccount groupOwner;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id", nullable = false, updatable = false)
    private UserGroupEntity group;

    public List<ThemeEntity> getAvailableThemes() {
        return availableThemes;
    }

    public void setAvailableThemes(List<ThemeEntity> availableThemes) {
        this.availableThemes = availableThemes;
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

    public UserGroupEntity getGroup() {
        return group;
    }

    public void setGroup(UserGroupEntity group) {
        this.group = group;
    }
}
