package com.bsu.server.dto;

import com.bsu.server.dto.security.UserAccount;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: HomeUser
 * Date: 15.4.12
 * Time: 9.49
 */
@Entity
@Table(name = "theme", schema = "public")
public class ThemeDto implements Serializable {
    @Id
    @Column(name = "theme_id")
    private Integer id;

    @OneToOne
    private UserAccount creator;

    @Column(name = "theme_name")
    private String name;

    @ManyToOne
    private CourseDto courseDto;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CourseDto getCourseDto() {
        return courseDto;
    }

    public UserAccount getCreator() {
        return creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
