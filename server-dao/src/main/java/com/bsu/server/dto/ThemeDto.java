package com.bsu.server.dto;

import com.bsu.server.dto.security.UserAccount;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * User: HomeUser
 * Date: 15.4.12
 * Time: 9.49
 */
@Entity(name = "theme")
public class ThemeDto {
    @Id
    private Integer id;

    @OneToOne
    private UserAccount creator;

    @Column(name = "theme_name")
    private String name;

    @OneToOne
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
