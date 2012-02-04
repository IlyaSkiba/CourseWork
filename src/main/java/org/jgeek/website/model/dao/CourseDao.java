package org.jgeek.website.model.dao;

import org.jgeek.website.model.dto.CourseDto;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Meloman
 * Date: 27.01.12
 * Time: 18:42
 */
@Transactional
public class CourseDao {

    @PersistenceContext
    private EntityManager em;


    @Transactional
    public void save(CourseDto course) {

    }

    public List<CourseDto> load(List<String> courseId) {
        List<CourseDto> result = new ArrayList<CourseDto>();
        Query q = em.createQuery("from CourseDto where id in :id");
        q = q.setParameter("id", (String[]) courseId.toArray());
        result = (List<CourseDto>) q.getResultList();
        return result;
    }
}
