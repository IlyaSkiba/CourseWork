package com.bsu.server.global.service.course;

import com.bsu.server.assembler.CourseAssembler;
import com.bsu.server.controller.CourseController;
import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.CourseEntity;
import com.bsu.server.global.service.base.BaseSearchableServiceImpl;
import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.global.admin.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ilya SKiba
 *         Date: 08.10.12
 */
@Service
public class CourseServiceImpl extends BaseSearchableServiceImpl<CourseDto, CourseEntity> implements CourseService {
    @Autowired
    private CourseController courseController;
    @Autowired
    private CourseAssembler courseAssembler;

    @Override
    public CourseDto getCourse(Integer id) {
        return courseAssembler.assemble(courseController.getById(id));
    }

    @Override
    public void updateCourse(CourseDto modifiedCourse) {
        courseController.update(courseAssembler.dissassemble(modifiedCourse));
    }

    @Override
    public void deleteCourse(Integer id) {
        courseController.delete(id);
    }

    @Override
    public CourseDto createCourse(CourseDto newCourse) {
        return courseAssembler.assemble(
                courseController.create(
                        courseAssembler.dissassemble(newCourse)));
    }

    @Override
    public List<CourseDto> getCourses() {
        List<CourseEntity> courses = courseController.getList();
        List<CourseDto> resultList = new ArrayList<>(courses.size());
        for (CourseEntity entity : courses) {
            resultList.add(courseAssembler.assemble(entity));
        }
        return resultList;
    }

    @Override
    protected CourseDto convert(CourseEntity entity) {
        return courseAssembler.assemble(entity);
    }

    @Override
    protected BaseController<CourseEntity> getController() {
        return courseController;
    }
}
