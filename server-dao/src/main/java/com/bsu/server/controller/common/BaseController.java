package com.bsu.server.controller.common;

import com.bsu.server.dto.BaseEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

/**
 * @author HomeUser
 *         Date: 19.2.13
 *         Time: 21.05
 */
@Transactional(readOnly = true)
public abstract class BaseController<T extends BaseEntity> {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<T> search(Map<String, String> filters, String orderField, boolean asc, int from,
                          int pageSize) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
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
        Root<T> root = query.from(getEntityClass());
        query.select(builder.count(root));
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            query = query.where(root.get(entry.getKey()).in(entry.getValue()));
        }
        return em.createQuery(query).getSingleResult();
    }

    protected abstract Class<T> getEntityClass();

    @Transactional(readOnly = true)
    public T getById(Integer id) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query = query.where(root.get("id").in(id));
        T result = em.createQuery(query).getSingleResult();

        return result;
    }

    @Transactional(readOnly = false)
    public T create(T assemble) {
        em.persist(assemble);
        return getById(assemble.getId());
    }

    @Transactional(readOnly = false)
    public T update(T entity) {
        return em.merge(entity);
    }

    @Transactional(readOnly = false)
    public void delete(Integer id) {
        em.remove(getById(id));
    }

    public List<T> getList() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getEntityClass());
        query.from(getEntityClass());
        return em.createQuery(query).getResultList();
    }
}
