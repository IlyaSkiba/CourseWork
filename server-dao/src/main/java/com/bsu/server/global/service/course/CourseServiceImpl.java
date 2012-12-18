package com.bsu.server.global.service.course;

import com.bsu.server.assembler.CourseAssembler;
import com.bsu.server.controller.CourseController;
import com.bsu.service.api.dto.CourseDto;
import com.bsu.service.api.global.admin.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ilya SKiba
 *         Date: 08.10.12
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseController courseController;
    @Autowired
    private CourseAssembler courseAssembler;

    @Override
    public CourseDto getCourse(Integer id) {
        return courseAssembler.assemble(courseController.getEntity(id));
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
}
