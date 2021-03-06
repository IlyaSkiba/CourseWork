package com.bsu.server.dto;

import com.bsu.server.dto.security.UserAccount;
import com.bsu.server.theoretic.test.dto.TestEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
    @Column
    private String informationURL;
    @ManyToOne(targetEntity = CourseEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_course", referencedColumnName = "id")
    private CourseEntity courseEntity;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "theme_group",
            joinColumns = {@JoinColumn(name = "theme", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "course_group", referencedColumnName = "id")})
    private List<CourseGroupEntity> assignedGroups;
    @ManyToMany
    @JoinTable
    private List<ThemeEntity> parentThemes;
    @OneToOne(mappedBy = "relatedTheme", cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    private TestEntity relatedTest;

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

    public TestEntity getRelatedTest() {
        return relatedTest;
    }

    public void setRelatedTest(TestEntity relatedTest) {
        this.relatedTest = relatedTest;
    }

    public String getInformationURL() {
        return informationURL;
    }

    public void setInformationURL(String informationURL) {
        this.informationURL = informationURL;
    }

    public static enum Significance {
        LOW(1), AVERAGE(2), HIGH(3);
        private int nonConverted;

        private Significance(int val) {
            nonConverted = val;
        }

        public boolean lower(Significance significance) {
            if (significance == null) {
                return false;
            }
            switch (significance) {
                case LOW:
                    return false;
                case AVERAGE:
                    return this == LOW;
                case HIGH:
                    return this == LOW || this == AVERAGE;
            }
            return true;
        }

        public boolean lowerEqual(Significance significance) {
            if (significance == null) {
                return false;
            }
            switch (significance) {
                case LOW:
                    return this == LOW;
                case AVERAGE:
                    return this == LOW || this == AVERAGE;
            }
            return true;
        }

        public static Significance parse(int significance) {
            for (Significance significance1 : Significance.values()) {
                if (significance1.nonConverted == significance) {
                    return significance1;
                }
            }
            return null;
        }

        public int getNonConverted() {
            return nonConverted;
        }


    }
}
