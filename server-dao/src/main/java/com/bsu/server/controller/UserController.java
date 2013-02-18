package com.bsu.server.controller;

import com.bsu.server.dto.security.UserAccount;
import com.bsu.service.api.global.admin.dto.UserDto;
import org.apache.commons.lang.StringUtils;
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
import java.util.Map;
import java.util.Set;

/**
 * @author Ilya Skiba
 */
@Service
public class UserController {

    @PersistenceContext
    private EntityManager em;
    private List<UserDto> users;

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

    @Transactional(readOnly = true)
    public List<UserAccount> getUsers() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<UserAccount> query = builder.createQuery(UserAccount.class);
        query.from(UserAccount.class);
        return em.createQuery(query).getResultList();
    }

    @Transactional(readOnly = true)
    public List<UserAccount> searchForUsers(Map<String, String> filters, String orderField, boolean asc, int from,
                                            int pageSize) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<UserAccount> query = builder.createQuery(UserAccount.class);
        Root<UserAccount> root = query.from(UserAccount.class);
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            query = query.where(root.get(entry.getKey()).in(entry.getValue()));
        }
        if (StringUtils.isNotEmpty(orderField)) {
            if (asc) {
                query = query.orderBy(builder.asc(root.get(orderField)));
            } else {
                query = query.orderBy(builder.desc(root.get(orderField)));
            }
        }
        return em.createQuery(query).setFirstResult(from).setMaxResults(pageSize).getResultList();
    }

    @Transactional(readOnly = true)
    public Long count(Map<String, String> filters) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<UserAccount> root = query.from(UserAccount.class);
        query.select(builder.count(root));
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            query = query.where(root.get(entry.getKey()).in(entry.getValue()));
        }
        return em.createQuery(query).getSingleResult();
    }

    @Transactional(readOnly = false)
    public UserAccount createUser(UserAccount assemble) {
        em.persist(assemble);
        return getUser(assemble.getId());
    }

    @Transactional(readOnly = true)
    public UserAccount getUserByUsername(String username) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<UserAccount> query = builder.createQuery(UserAccount.class);
        Root<UserAccount> root = query.from(UserAccount.class);
        query = query.where((root.get("username").in(username)));
        return em.createQuery(query).getSingleResult();
    }

    @Transactional(readOnly = false)
    public UserAccount update(UserAccount newUser) {
        return em.merge(newUser);
    }

    @Transactional(readOnly = false)
    public void delete(Integer userId) {
        em.remove(getUser(userId));
        //em.createQuery("delete from UserAccount where id=:userId").setParameter("userId", userId).executeUpdate();
    }
}
