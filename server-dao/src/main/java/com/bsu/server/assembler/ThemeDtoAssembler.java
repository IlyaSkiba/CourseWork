package com.bsu.server.assembler;

import com.bsu.server.dto.ThemeEntity;
import com.bsu.service.api.dto.ThemeDto;

/**
 * Created with IntelliJ IDEA.
 * User: meloman
 * Date: 30.08.12
 * Time: 22:27
 */
public class ThemeDtoAssembler {

    public static ThemeDto assemble(ThemeEntity entity) {
        ThemeDto dto = new ThemeDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatorName(entity.getCreator().getUsername());
        return dto;
    }

    public static ThemeEntity disassemble(ThemeDto dto) {
        ThemeEntity newEntity = new ThemeEntity();
        newEntity.setId(dto.getId());
        newEntity.setName(dto.getName());
        return newEntity;
    }
}
