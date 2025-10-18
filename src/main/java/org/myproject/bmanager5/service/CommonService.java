package org.myproject.bmanager5.service;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.myproject.bmanager5.converter.CommonConverter;
import org.myproject.bmanager5.converter.SearchRequestConverter;
import org.myproject.bmanager5.dto.request.SearchRequest;
import org.myproject.bmanager5.model.CategoryModel;
import org.myproject.bmanager5.repository.CommonRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommonService implements ServiceInterface {
    private final CommonRepository commonRepository;
    private final CommonConverter commonConverter;
    private final SearchRequestConverter searchRequestConverter;

    @Override
    public List<CategoryModel> search(SearchRequest request) {
        Pageable pageable = searchRequestConverter.getPageable(request);
        Specification<CategoryModel> specification = searchRequestConverter.getSpecification(request);

        List<CategoryModel> result = commonRepository.findAll(specification, pageable).getContent();

        result = result.stream()
                .map(commonConverter::fillIdIndexes)
                .toList();

        return result;
    }

    @Override
    public CategoryModel get(@NotNull Long id) {
        return commonConverter.fillIdIndexes(
                commonRepository.findById(id).orElseThrow()
        );
    }

    @Override
    public CategoryModel create(CategoryModel source) {
        commonConverter.fillIdObjects(source);

        commonRepository.save(source);

        source = commonRepository.findById(source.getId()).orElseThrow();

        commonConverter.fillIdIndexes(source);

        return source;
    }

    @Override
    public CategoryModel update(@NotNull Long id, CategoryModel source) {
        CategoryModel model = commonRepository.findById(id).orElseThrow();

        commonConverter.updateModel(model, source);

        commonConverter.fillIdObjects(model);

        commonRepository.save(model);

        model = commonRepository.findById(id).orElseThrow();

        commonConverter.fillIdIndexes(model);

        return model;
    }
}
