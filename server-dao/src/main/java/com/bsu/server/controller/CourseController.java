package com.bsu.server.controller;

/**
 * @author : Ilya Skiba
 * Date: 15.4.12
 * Time: 9.33
 * This class represents DAO of the course entity. Will be modified to add course owner
 */

import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.CourseEntity;
import com.bsu.server.dto.ThemeEntity;
import com.bsu.server.dto.UserGroupEntity;
import com.bsu.server.dto.security.UserAccount;
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

    public List<CourseEntity> loadCourseList(Integer userId) {
        UserAccount currentUser = em.createQuery("from UserAccount where id=:userId", UserAccount.class)
                .setParameter("userId", userId).getSingleResult();
        List<UserGroupEntity> userGroups = currentUser.getUserGroups();
        HashSet<CourseEntity> courses = new HashSet<>();
        if (userGroups == null) {
            return Collections.emptyList();
        }
        //TODO: fix this!
        for (UserGroupEntity userGroup : userGroups) {
            for (ThemeEntity theme : userGroup.getCourses().get(0).getAvailableThemes()) {
                courses.add(theme.getCourseEntity());
            }
        }
        return new ArrayList<>(courses);
    }

    @Override
    protected Class<CourseEntity> getEntityClass() {
        return CourseEntity.class;
    }

}
