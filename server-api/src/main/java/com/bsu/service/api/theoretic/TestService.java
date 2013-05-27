package com.bsu.service.api.theoretic;

import com.bsu.service.api.base.SearchableService;
import com.bsu.service.api.dto.TestDto;

/**
 * @author HomeUser
 *         Date: 27.5.13
 *         Time: 23.07
 */
public interface TestService extends SearchableService<TestDto> {
    TestDto getByThemeId(Integer themeId);
}
