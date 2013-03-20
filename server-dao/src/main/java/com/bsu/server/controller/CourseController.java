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
import com.bsu.server.dto.UserGroupEntity;
import com.bsu.server.dto.security.UserAccount;
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
    private UserController userController;

    public List<CourseEntity> loadCourseList(Integer userId) {
        UserAccount currentUser = userController.getById(userId);
        List<UserGroupEntity> userGroups = currentUser.getUserGroups();
        HashSet<CourseEntity> courses = new HashSet<>();
        if (userGroups == null) {
            return Collections.emptyList();
        }
        for (UserGroupEntity userGroup : userGroups) {
            for (CourseGroupEntity courseGroup : userGroup.getCourses()) {
                courses.add(courseGroup.getAssignedCourse());
            }
        }
        return new ArrayList<>(courses);
    }

    @Override
    protected Class<CourseEntity> getEntityClass() {
        return CourseEntity.class;
    }

}
