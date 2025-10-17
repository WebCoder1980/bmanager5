package org.myproject.bmanager5.service;

import jakarta.validation.constraints.NotNull;
import org.myproject.bmanager5.dto.request.SearchRequest;
import org.myproject.bmanager5.model.CategoryModel;

import java.util.List;

public interface ServiceInterface {
    List<CategoryModel> search(SearchRequest request);

    CategoryModel get(@NotNull Long id);

    CategoryModel create(CategoryModel source);

    CategoryModel update(@NotNull Long id, CategoryModel source);
}
