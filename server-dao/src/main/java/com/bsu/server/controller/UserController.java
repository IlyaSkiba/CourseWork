package com.bsu.server.controller;

import com.bsu.server.dto.security.UserAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
}
