package com.bsu.service.api.base;

import com.bsu.service.api.dto.base.BaseDto;

/**
 * @author HomeUser
 *         Date: 25.3.13
 *         Time: 23.10
 */
public interface CRUDService<T extends BaseDto> {

    T createOrUpdate(T dto);

    T getById(Integer id);

    void delete(T dto);
}
