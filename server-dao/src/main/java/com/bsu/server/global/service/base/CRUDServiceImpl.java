package com.bsu.server.global.service.base;

import com.bsu.server.assembler.base.BaseConverter;
import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.BaseEntity;
import com.bsu.service.api.base.CRUDService;
import com.bsu.service.api.dto.base.BaseDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author HomeUser
 *         Date: 25.3.13
 *         Time: 21.30
 */
@Transactional
public abstract class CRUDServiceImpl<T extends BaseDto, D extends BaseEntity> implements CRUDService<T> {

    @Override
    public T createOrUpdate(T dto) {
        if (dto.getId() == null) {
            return getConverter().convert(getController().create(getConverter().convert(dto)));
        }
        return getConverter().convert(getController().update(getConverter().convert(dto)));
    }

    @Override
    public T getById(Integer id) {
        return getConverter().convert(getController().getById(id));
    }

    @Override
    public void delete(T dto) {
        getConverter().convert(dto);
        getController().delete(dto.getId());
    }

    protected abstract BaseController<D> getController();

    protected abstract BaseConverter<T, D> getConverter();
}
