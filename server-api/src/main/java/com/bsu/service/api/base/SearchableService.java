package com.bsu.service.api.base;

import java.util.List;
import java.util.Map;

/**
 * @author HomeUser
 *         Date: 21.2.13
 *         Time: 2.14
 */
public interface SearchableService<T> {
    int count(Map<String, String> filters);

    List<T> search(Map<String, String> filters, String sortField, String sortOrder, int first, int pageSize);
}
