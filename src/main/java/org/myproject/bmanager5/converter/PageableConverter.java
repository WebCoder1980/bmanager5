package org.myproject.bmanager5.converter;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageableConverter {
    public Pageable toPageable(Integer start, Integer size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(sortBy);
        if (sortDirection.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        }

        return PageRequest.of(start, size, sort);
    }

    public String toSQL(Integer pageId, Integer pageSize, String sortBy, String sortDirection) {
        final Long limit = (long) pageSize;
        final Long offset = (long)pageId*pageSize;

        StringBuilder result = new StringBuilder();

        result.append(String.format("ORDER BY %s %s\n", sortBy, sortDirection));
        result.append(String.format("LIMIT %d OFFSET %d", limit, offset));

        return result.toString();
    }
}
