package org.myproject.bmanager5.service;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.myproject.bmanager5.converter.CategoryConverter;
import org.myproject.bmanager5.converter.SearchRequestConverter;
import org.myproject.bmanager5.dto.request.SearchRequest;
import org.myproject.bmanager5.model.CategoryModel;
import org.myproject.bmanager5.repository.CategoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService implements ServiceInterface {
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;
    private final SearchRequestConverter searchRequestConverter;

    @Override
    public List<CategoryModel> search(SearchRequest request) {
        Pageable pageable = searchRequestConverter.getPageable(request);
        Specification<CategoryModel> specification = searchRequestConverter.getSpecification(request);

        List<CategoryModel> result = categoryRepository.findAll(specification, pageable).getContent();

        result = result.stream()
                .map(categoryConverter::fillIdIndexes)
                .toList();

        return result;
    }

    @Override
    public CategoryModel get(@NotNull Long id) {
        return categoryConverter.fillIdIndexes(
                categoryRepository.findById(id).orElseThrow()
        );
    }

    @Override
    public CategoryModel create(CategoryModel source) {
        categoryConverter.fillIdObjects(source);

        categoryRepository.save(source);

        source = categoryRepository.findById(source.getId()).orElseThrow();

        categoryConverter.fillIdIndexes(source);

        return source;
    }

    @Override
    public CategoryModel update(@NotNull Long id, CategoryModel source) {
        CategoryModel model = categoryRepository.findById(id).orElseThrow();

        categoryConverter.updateModel(model, source);

        categoryConverter.fillIdObjects(model);

        categoryRepository.save(model);

        model = categoryRepository.findById(id).orElseThrow();

        categoryConverter.fillIdIndexes(model);

        return model;
    }
}
