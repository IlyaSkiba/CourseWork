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
import com.bsu.server.dto.QCourseEntity;
import com.bsu.service.api.dto.CourseDto;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class CourseController extends BaseController<CourseEntity> {
    @PersistenceContext
    private EntityManager em;

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

    public List<CourseEntity> loadCourseListByOwnerId(Integer ownerId){
        JPQLQuery query = new JPAQuery(em);
        return query.from(QCourseEntity.courseEntity)
                .where(QCourseEntity.courseEntity.owner.id.eq(ownerId)).list(QCourseEntity.courseEntity);

    }

    @Override
    protected Class<CourseEntity> getEntityClass() {
        return CourseEntity.class;
    }

}
