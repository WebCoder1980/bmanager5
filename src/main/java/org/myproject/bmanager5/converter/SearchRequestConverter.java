package org.myproject.bmanager5.converter;

import io.github.perplexhub.rsql.RSQLJPASupport;
import org.myproject.bmanager5.dto.request.SearchRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SearchRequestConverter {
    @Deprecated
    public Pageable toPageable(Integer start, Integer size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(sortBy);
        if (sortDirection.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        }

        return PageRequest.of(start, size, sort);
    }

    public <T> Specification<T> getSpecification(SearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPageStart(), request.getPageSize());

        @SuppressWarnings("unchecked")
        Specification<T> result = (Specification<T>) RSQLJPASupport.toSpecification(request.getRsqlFilter())
                .and(RSQLJPASupport.toSort(request.getRsqlSort()));

        return result;
    }

    public Pageable getPageable(SearchRequest request) {
        return PageRequest.of(request.getPageStart(), request.getPageSize());
    }
}
