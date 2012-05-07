package com.bsu.server.controller;

/**
 * @author : Ilya Skiba
 * Date: 15.4.12
 * Time: 9.33
 * This class represents DAO of the course entity. Will be modified to add course owner
 */

import com.bsu.server.dto.CourseDto;
import com.bsu.server.dto.ThemeDto;
import com.bsu.server.dto.UserGroupDto;
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
public class CourseController {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<CourseDto> loadCourseList(Integer userId) {
        UserAccount currentUser = em.createQuery("from UserAccount where id=:userId", UserAccount.class)
                .setParameter("userId", userId).getSingleResult();
        List<UserGroupDto> userGroups = currentUser.getUserGroups();
        HashSet<CourseDto> courses = new HashSet<CourseDto>();
        if (userGroups == null) return Collections.emptyList();
        for (UserGroupDto userGroup : userGroups) {
            for (ThemeDto theme : userGroup.getAvailableThemes()) {
                courses.add(theme.getCourseDto());
            }
        }
        return new ArrayList<CourseDto>(courses);
    }
}
