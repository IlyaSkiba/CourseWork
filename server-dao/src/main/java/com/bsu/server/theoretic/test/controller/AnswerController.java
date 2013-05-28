package com.bsu.server.theoretic.test.controller;

import com.bsu.server.controller.common.BaseController;
import com.bsu.server.theoretic.test.dto.AnswerEntity;
import org.springframework.stereotype.Repository;

/**
 * @author HomeUser
 *         Date: 28.5.13
 *         Time: 16.22
 */
@Repository
public class AnswerController extends BaseController<AnswerEntity> {
    @Override
    protected Class<AnswerEntity> getEntityClass() {
        return AnswerEntity.class;
    }
}
