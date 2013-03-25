package com.bsu.server.assembler.base;

import com.bsu.server.dto.BaseEntity;

/**
 * @author HomeUser
 *         Date: 25.3.13
 *         Time: 22.02
 */
public abstract class BaseConverter<T, D extends BaseEntity> {
    public abstract T convert(D entity);

    public abstract D convert(T entity);
}
