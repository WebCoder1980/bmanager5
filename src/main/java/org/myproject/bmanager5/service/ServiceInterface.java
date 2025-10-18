package org.myproject.bmanager5.service;

import jakarta.validation.constraints.NotNull;
import org.myproject.bmanager5.dto.request.SearchRequest;

import java.util.List;

public interface ServiceInterface<T> {
    List<T> search(SearchRequest request);

    T get(@NotNull Long id);

    T create(T source);

    T update(@NotNull Long id, T source);
}
