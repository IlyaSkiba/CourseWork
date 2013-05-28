package com.bsu.server.assembler;

import com.bsu.server.assembler.base.BaseConverter;
import com.bsu.server.theoretic.test.controller.AnswerController;
import com.bsu.server.theoretic.test.dto.AnswerEntity;
import com.bsu.service.api.dto.AnswerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author HomeUser
 *         Date: 28.5.13
 *         Time: 16.14
 */
@Component
public class AnswerAssembler extends BaseConverter<AnswerDto, AnswerEntity> {
    @Autowired
    private AnswerController answerController;

    @Override
    public AnswerDto convert(AnswerEntity entity) {
        AnswerDto dto = new AnswerDto();
        dto.setId(entity.getId());
        dto.setRight(entity.getRight());
        dto.setAnswer(entity.getTextAnswer());
        return dto;
    }

    @Override
    public AnswerEntity convert(AnswerDto dto) {
        AnswerEntity entity = new AnswerEntity();
        if (dto.getId() != null && dto.getId() != 0) {
            entity = answerController.getById(dto.getId());
        }
        entity.setRight(dto.isRight());
        entity.setTextAnswer(dto.getAnswer());
        return entity;
    }
}
