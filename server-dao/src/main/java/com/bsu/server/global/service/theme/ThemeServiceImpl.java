package com.bsu.server.global.service.theme;

import com.bsu.server.assembler.ThemeDtoAssembler;
import com.bsu.server.controller.ThemeController;
import com.bsu.server.dto.ThemeEntity;
import com.bsu.service.api.dto.ThemeDto;
import com.bsu.service.api.theoretic.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ilya Skiba
 */
@Service
public class ThemeServiceImpl implements ThemeService {
    @Autowired
    private ThemeController themeController;

    @Override
    public boolean update(ThemeDto themeDto) {
        try {
            ThemeEntity entity = ThemeDtoAssembler.disassemble(themeDto);
            themeController.updateEntity(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void create(ThemeDto themeDto) {
        ThemeEntity entity = ThemeDtoAssembler.disassemble(themeDto);
        themeController.createTheme(entity);
    }

    @Override
    public boolean delete(Integer themeId) {
        return themeController.deleteTheme(themeId);
    }

}
