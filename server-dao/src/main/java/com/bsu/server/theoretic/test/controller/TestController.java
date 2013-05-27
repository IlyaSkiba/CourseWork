package com.bsu.server.theoretic.test.controller;

import com.bsu.server.controller.common.BaseController;
import com.bsu.server.theoretic.test.dto.TestEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * @author Ilya Skiba
 */
@Repository
@Transactional
public class TestController extends BaseController<TestEntity> {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = false)
    public TestEntity getTestFromTheme(Integer themeId) {
        @SuppressWarnings("JpaQlInspection")
        TypedQuery<TestEntity> testDtoQuery =
                em.createQuery(" select x from TestEntity as x where x.relatedTheme.id = :themeId", TestEntity.class);
        testDtoQuery.setParameter("themeId", themeId);
        if (testDtoQuery.getResultList().size() == 0) {
            return null;
        }
        return testDtoQuery.getSingleResult();
    }

    @Override
    protected Class<TestEntity> getEntityClass() {
        return TestEntity.class;
    }
}
