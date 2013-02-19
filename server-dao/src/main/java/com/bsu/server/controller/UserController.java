package com.bsu.server.controller;

import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.security.UserAccount;
import com.bsu.service.api.global.admin.dto.UserDto;
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
public class UserController extends BaseController<UserAccount> {

    @PersistenceContext
    private EntityManager em;
    private List<UserDto> users;

    @Transactional(readOnly = true)
    public List<UserAccount> getUsersByRoles(Set<String> roles) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<UserAccount> query = builder.createQuery(UserAccount.class);
        Root<UserAccount> root = query.from(UserAccount.class);
        Path<String> roleName = root.join("userRoles").get("roleName");

        query = query.where(roleName.in(roles));
        return em.createQuery(query).getResultList();
    }

    @Transactional(readOnly = true)
    public List<UserAccount> getUsers() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<UserAccount> query = builder.createQuery(UserAccount.class);
        query.from(UserAccount.class);
        return em.createQuery(query).getResultList();
    }


    @Transactional(readOnly = true)
    public UserAccount getUserByUsername(String username) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<UserAccount> query = builder.createQuery(UserAccount.class);
        Root<UserAccount> root = query.from(UserAccount.class);
        query = query.where((root.get("username").in(username)));
        return em.createQuery(query).getSingleResult();
    }

    @Override
    protected Class<UserAccount> getEntityClass() {
        return UserAccount.class;
    }
}
