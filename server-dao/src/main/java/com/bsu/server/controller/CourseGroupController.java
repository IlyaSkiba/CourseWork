package com.bsu.server.controller;

import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.CourseGroupEntity;
import com.bsu.server.dto.QCourseGroupEntity;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author HomeUser
 *         Date: 21.2.13
 *         Time: 2.44
 */
@Repository
public class CourseGroupController extends BaseController<CourseGroupEntity> {
    @PersistenceContext
    private EntityManager em;

    @Override
    protected Class<CourseGroupEntity> getEntityClass() {
        return CourseGroupEntity.class;
    }

    public List<CourseGroupEntity> getAssignedCourses(Integer courseId) {
        JPQLQuery query = new JPAQuery(em);
        return query.from(QCourseGroupEntity.courseGroupEntity)
                .where(QCourseGroupEntity.courseGroupEntity.assignedCourse.id
                        .eq(courseId)).list(QCourseGroupEntity.courseGroupEntity);

    }

    public List<CourseGroupEntity> getCoursesForGroup(Integer groupId) {
        JPQLQuery query = new JPAQuery(em);
        return query.from(QCourseGroupEntity.courseGroupEntity)
                .where(QCourseGroupEntity.courseGroupEntity.group.id
                        .eq(groupId)).list(QCourseGroupEntity.courseGroupEntity);

    }
}
