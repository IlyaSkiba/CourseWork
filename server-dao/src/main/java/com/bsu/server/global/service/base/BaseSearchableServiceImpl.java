package com.bsu.server.global.service.base;

import com.bsu.server.controller.common.BaseController;
import com.bsu.server.dto.BaseEntity;
import com.bsu.service.api.base.SearchableService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.avro.reflect.Nullable;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author HomeUser
 *         Date: 21.2.13
 *         Time: 2.17
 */
public abstract class BaseSearchableServiceImpl<T, D extends BaseEntity> implements SearchableService<T> {
    @Override
    public int count(Map<String, String> filters) {
        return Integer.parseInt(getController().count(filters).toString());
    }

    @Override
    public List<T> search(Map<String, String> filters, String sortField, String sortOrder, int first, int pageSize) {
        return Lists.transform(getController().search(filters, sortField, StringUtils.equals(sortOrder, "ASCENDING"),
                first, pageSize), new TransformFunction());
    }

    protected abstract T convert(D entity);

    protected abstract BaseController<D> getController();

    public class TransformFunction implements Function<D, T> {

        @Override
        public T apply(@Nullable D input) {
            return input == null ? null : convert(input);
        }
    }
}
