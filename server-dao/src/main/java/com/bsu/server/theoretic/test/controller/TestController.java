package com.bsu.server.theoretic.test.controller;

import com.bsu.server.theoretic.test.dto.TestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * @author Ilya Skiba
 */
@Service
public class TestController {
    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = false)
    public TestDto getTest(Integer testId) {
        TypedQuery<TestDto> testDtoQuery = em.createQuery("from TestDto where id = :testId", TestDto.class);
        testDtoQuery.setParameter("testId", testId);
        return testDtoQuery.getSingleResult();
    }

    @Transactional(readOnly = false)
    public TestDto getTestFromTheme(Integer themeId) {
        TypedQuery<TestDto> testDtoQuery = em.createQuery("from TestDto where relatedTheme.id = :themeId", TestDto.class);
        testDtoQuery.setParameter("themeId", themeId);
        return testDtoQuery.getSingleResult();
    }
}
