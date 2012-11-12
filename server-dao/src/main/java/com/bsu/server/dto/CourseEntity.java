package com.bsu.server.dto;

import com.bsu.server.dto.security.UserAccount;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Meloman
 * Date: 27.01.12
 * Time: 18:26
 */
@Entity
@Table(name = "course", schema = "public")
public class CourseEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "course_id")
    private Integer id;
    @NotNull
    @Length(max = 500)
    @Column(name = "course_name")
    private String courseName;
    @OneToMany
    private List<ThemeEntity> themes;
    @ManyToOne
    private UserAccount owner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<ThemeEntity> getThemes() {
        return themes;
    }

    public void setThemes(List<ThemeEntity> themes) {
        this.themes = themes;
    }

    public UserAccount getOwner() {
        return owner;
    }

    public void setOwner(UserAccount owner) {
        this.owner = owner;
    }
}
