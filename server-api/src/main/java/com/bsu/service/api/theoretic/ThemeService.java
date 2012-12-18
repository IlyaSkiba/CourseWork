package com.bsu.service.api.theoretic;

import com.bsu.service.api.dto.ThemeDto;

import java.io.Serializable;

/**
 * @author Ilya Skiba
 */
public interface ThemeService extends Serializable {
    public boolean updateTheme(ThemeDto themeDto);

    public void createTheme(ThemeDto themeDto);

    boolean deleteTheme(Integer themeId);
}
