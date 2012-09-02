package com.bsu.server.assembler;

import com.bsu.server.dto.ThemeEntity;
import com.bsu.service.api.dto.ThemeDto;

/**
 * Created with IntelliJ IDEA.
 * User: meloman
 * Date: 30.08.12
 * Time: 22:27
 * To change this template use File | Settings | File Templates.
 */
public class ThemeDtoAssembler {

    public static final ThemeDto assemble(ThemeEntity entity) {
        ThemeDto dto = new ThemeDto();
        return dto;
    }

    public static final ThemeEntity disassemble(ThemeDto dto) {
        ThemeEntity newEntity = new ThemeEntity();
        return newEntity;
    }
}
