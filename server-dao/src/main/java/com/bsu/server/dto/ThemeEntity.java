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
    @ManyToMany
    @JoinTable
    private List<ThemeEntity> parentThemes;

    @Column(name = "significance")
    private Significance significance = Significance.LOW;

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

    public List<ThemeEntity> getParentThemes() {
        return parentThemes;
    }

    public void setParentThemes(List<ThemeEntity> parentThemes) {
        this.parentThemes = parentThemes;
    }

    public Significance getSignificance() {
        return significance;
    }

    public void setSignificance(Significance significance) {
        this.significance = significance;
    }

    public static enum Significance {
        LOW, AVERAGE, HIGH
    }
}
