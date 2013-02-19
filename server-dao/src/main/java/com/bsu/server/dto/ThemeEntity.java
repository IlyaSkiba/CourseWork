package com.bsu.server.dto;

import com.bsu.server.dto.security.UserAccount;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * User: HomeUser
 * Date: 15.4.12
 * Time: 9.49
 */
@Entity
@Table(name = "theme", schema = "public")
public class ThemeEntity extends BaseEntity implements Serializable {
    @OneToOne
    private UserAccount creator;
    @Column(name = "theme_name")
    private String name;
    @ManyToOne
    private CourseEntity courseEntity;
    @ManyToMany
    @JoinTable
    private List<CourseGroupEntity> assignedGroups;

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

    public List<CourseGroupEntity> getAssignedGroups() {
        return assignedGroups;
    }

    public void setAssignedGroups(List<CourseGroupEntity> assignedGroups) {
        this.assignedGroups = assignedGroups;
    }
}
