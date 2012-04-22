package com.bsu.server.controller;

/**
 * @author : Ilya Skiba
 * Date: 15.4.12
 * Time: 9.33
 * This class represents DAO of the course entity. Will be modified to add course owner
 */

import com.bsu.server.dto.CourseDto;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class CourseController {

    @PersistenceContext
    private EntityManager em;

    public List<CourseDto> loadCourseList() {
        return (List<CourseDto>) em.createQuery("from CourseDto order by courseName").getResultList();
    }
}
