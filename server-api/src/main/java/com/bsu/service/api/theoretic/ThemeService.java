package com.bsu.service.api.theoretic;

import com.bsu.service.api.dto.ThemeDto;

import java.io.Serializable;

/**
 * @author Ilya Skiba
 */
public interface ThemeService extends Serializable {
    public boolean update(ThemeDto themeDto);

    public void create(ThemeDto themeDto);

    boolean delete(Integer themeId);
}
