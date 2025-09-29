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
}
