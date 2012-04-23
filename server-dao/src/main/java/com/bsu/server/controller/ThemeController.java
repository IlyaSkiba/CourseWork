package com.bsu.server.controller;

import com.bsu.server.dto.ThemeDto;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author : Ilya Skiba
 *         Date: 15.4.12
 *         Time: 10.20
 */

@Service
public class ThemeController {
    @PersistenceContext
    private EntityManager em;

    public List<ThemeDto> getThemesForCourse(Integer courseId) {
        if (courseId == null) return Collections.emptyList();
        TypedQuery<ThemeDto> q = em.createQuery("from ThemeDto where courseDto.id in (:courseId)", ThemeDto.class);
        q.setParameter("courseId", Arrays.asList(courseId));
        return q.getResultList();
    }
}
