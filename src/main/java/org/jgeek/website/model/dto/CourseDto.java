package org.jgeek.website.model.dto;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Meloman
 * Date: 27.01.12
 * Time: 18:26
 */
@Entity
@Table(name = "course", schema = "public")
public class CourseDto implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "course_id")
    private Integer id;

    @NotNull
    @Length(max = 100)
    @Column(name = "course_name")
    private String courseName;


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
}
