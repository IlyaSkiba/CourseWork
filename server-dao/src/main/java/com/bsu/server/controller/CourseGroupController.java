package com.bsu.server.controller;

import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.CourseGroupEntity;
import com.bsu.server.dto.QCourseGroupEntity;
import com.bsu.service.api.dto.ThemeDto;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private ThemeController themeController;

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

    public List<CourseGroupEntity> getCoursesForUser(Integer userId) {
        JPQLQuery query = new JPAQuery(em);
        return query.from(QCourseGroupEntity.courseGroupEntity)
                .where(QCourseGroupEntity.courseGroupEntity.group.assignedUsers.any().id.eq(userId))
                .orderBy(QCourseGroupEntity.courseGroupEntity.assignedCourse.courseName.asc())
                .list(QCourseGroupEntity.courseGroupEntity);
    }

    public CourseGroupEntity getAvailableThemesForUser(Integer userId, Integer courseId) {
        JPQLQuery query = new JPAQuery(em);
        return query.from(QCourseGroupEntity.courseGroupEntity)
                .where(QCourseGroupEntity.courseGroupEntity.group.assignedUsers.any().id
                        .eq(userId).and(QCourseGroupEntity.courseGroupEntity.assignedCourse.id.eq(courseId)))
                .singleResult(QCourseGroupEntity.courseGroupEntity);
    }


    @Transactional(readOnly = false)
    public void addThemeToAllCourses(ThemeDto newTheme) {
        List<CourseGroupEntity> courses = getAssignedCourses(newTheme.getCourseId());
        for (CourseGroupEntity targetGroup : courses) {
            targetGroup.getAvailableThemes().add(themeController.getById(newTheme.getId()));
            em.merge(targetGroup);
        }
        em.flush();
    }

    public List<CourseGroupEntity> getGroupsForCourse(Integer courseDtoId) {
        JPQLQuery query = new JPAQuery(em);
        return query.from(QCourseGroupEntity.courseGroupEntity)
                .where(QCourseGroupEntity.courseGroupEntity.assignedCourse.id.eq(courseDtoId))
                .list(QCourseGroupEntity.courseGroupEntity);
    }
}
