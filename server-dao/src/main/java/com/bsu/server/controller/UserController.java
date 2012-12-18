package com.bsu.server.controller;

import com.bsu.server.dto.security.UserAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;

/**
 * @author Ilya Skiba
 */
@Service
public class UserController {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public UserAccount getUser(Integer id) {
        TypedQuery<UserAccount> query = em.createQuery("from UserAccount where id=:id", UserAccount.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Transactional(readOnly = true)
    public List<UserAccount> getUsersByRoles(Set<String> roles) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<UserAccount> query = builder.createQuery(UserAccount.class);
        Root<UserAccount> root = query.from(UserAccount.class);
        Path<String> roleName = root.join("userRoles").get("roleName");

        query = query.where(roleName.in(roles));
        return em.createQuery(query).getResultList();
    }
}
