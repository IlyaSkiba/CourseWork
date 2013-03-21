package com.bsu.server.controller;

/**
 * @author : Ilya Skiba
 * Date: 15.4.12
 * Time: 9.33
 * This class represents DAO of the course entity. Will be modified to add course owner
 */

import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.CourseEntity;
import com.bsu.server.dto.CourseGroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class CourseController extends BaseController<CourseEntity> {
    @Autowired
    private CourseGroupController courseGroupController;

    public List<CourseEntity> loadCourseList(Integer userId) {
        List<CourseGroupEntity> userGroups = courseGroupController.getCoursesForUser(userId);
        HashSet<CourseEntity> courses = new HashSet<>();
        if (userGroups == null) {
            return Collections.emptyList();
        }
        for (CourseGroupEntity userGroup : userGroups) {
            courses.add(userGroup.getAssignedCourse());
        }
        return new ArrayList<>(courses);
    }

    @Override
    protected Class<CourseEntity> getEntityClass() {
        return CourseEntity.class;
    }

}
