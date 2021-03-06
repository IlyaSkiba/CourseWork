package com.bsu.server.global.service.base;

import com.bsu.server.dto.BaseEntity;
import com.bsu.service.api.base.SearchableService;
import com.bsu.service.api.dto.base.BaseDto;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.avro.reflect.Nullable;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author HomeUser
 *         Date: 21.2.13
 *         Time: 2.17
 */
@Transactional
public abstract class BaseSearchableServiceImplImpl<T extends BaseDto, D extends BaseEntity>
        extends CRUDServiceImpl<T, D> implements SearchableService<T> {
    @Override
    public int count(Map<String, String> filters) {
        return Integer.parseInt(getController().count(filters).toString());
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> search(Map<String, String> filters, String sortField, String sortOrder, int first, int pageSize) {
        return Lists.transform(getController().search(filters, sortField, StringUtils.equals(sortOrder, "ASCENDING"),
                first, pageSize), new TransformFunction());
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> getBatchById(Iterable<Integer> entityIds) {
        List<T> resultList = Lists.newArrayList();
        for (Integer entityId : entityIds) {
            resultList.add(getById(entityId));
        }
        return resultList;
    }

    protected List<T> convertList(List<D> entities) {
        return convertList(entities, new TransformFunction());
    }

    public <From, To> List<To> convertList(List<From> entities, Function<From, To> convertFunction) {
        List<To> result = Lists.transform(entities, convertFunction);
        for (To to : result) {
            Validate.notNull(to);
        }
        return result;
    }


    public class TransformFunction implements Function<D, T> {

        @Override
        public T apply(@Nullable D input) {
            return input == null ? null : getConverter().convert(input);
        }
    }
}
