package com.bsu.server.global.service.course;

import com.bsu.server.assembler.CourseAssembler;
import com.bsu.server.assembler.base.BaseConverter;
import com.bsu.server.controller.CourseController;
import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.CourseEntity;
import com.bsu.server.global.service.base.BaseSearchableServiceImplImpl;
import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.global.admin.CourseService;
import com.bsu.service.api.global.admin.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ilya SKiba
 *         Date: 08.10.12
 */
@Service
public class CourseServiceImpl extends BaseSearchableServiceImplImpl<CourseDto, CourseEntity> implements CourseService {
    @Autowired
    private CourseController courseController;
    @Autowired
    private CourseAssembler courseAssembler;

    @Override
    public List<CourseDto> getCourses() {
        List<CourseEntity> courses = courseController.getList();
        return convertList(courses);
    }

    @Override
    public List<CourseDto> searchByOwnerId(Integer id) {
        return convertList(courseController.loadCourseListByOwnerId(id));
    }

    @Override
    public List<CourseDto> loadCourseList(UserDto user) {
        return convertList(courseController.loadCourseList(user.getId()));
    }

    @Override
    protected BaseController<CourseEntity> getController() {
        return courseController;
    }

    @Override
    public BaseConverter<CourseDto, CourseEntity> getConverter() {
        return courseAssembler;
    }
}
