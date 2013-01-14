package com.bsu.server.controller;

import com.bsu.server.dto.security.UserRole;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * @author HomeUser
 *         Date: 27.12.12
 *         Time: 20.27
 */
@Service
public class RoleController {
    @PersistenceContext
    private EntityManager em;

    public List<UserRole> getRoles() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<UserRole> query = builder.createQuery(UserRole.class);
        query.from(UserRole.class);
        return em.createQuery(query).getResultList();
    }
}
