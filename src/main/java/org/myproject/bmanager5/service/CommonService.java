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
public class CommonService<
        T extends CategoryModel,
        TR extends CommonRepository<T>
> implements ServiceInterface<T> {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final TR categoryRepository;
    private final CommonConverter<T, TR> commonConverter;
    private final SearchRequestConverter searchRequestConverter;

    @Override
    public List<T> search(SearchRequest request) {
        Pageable pageable = searchRequestConverter.getPageable(request);
        Specification<T> specification = searchRequestConverter.getSpecification(request);

        List<T> result = categoryRepository.findAll(specification, pageable)
                .getContent();

        result = result.stream()
                .map(commonConverter::fillIdIndexes)
                .map(i -> (T) i)
                .toList();

        return result;
    }

    @Override
    public T get(@NotNull Long id) {
        return (T) commonConverter.fillIdIndexes(
                categoryRepository.findById(id).orElseThrow()
        );
    }

    @Override
    public T create(T source) {
        commonConverter.fillIdObjects(source);

        categoryRepository.save(source);

        source = categoryRepository.findById((source).getId()).orElseThrow();

        commonConverter.fillIdIndexes(source);

        return source;
    }

    @Override
    public T update(@NotNull Long id, T source) {
        T model = categoryRepository.findById(id).orElseThrow();

        commonConverter.updateModel(model, source);

        commonConverter.fillIdObjects(model);

        categoryRepository.save(model);

        model = categoryRepository.findById(id).orElseThrow();

        commonConverter.fillIdIndexes(model);

        return model;
    }
}
