package org.myproject.bmanager5.service;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.myproject.bmanager5.converter.CategoryConverter;
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
    private final CategoryConverter<T> categoryConverter;
    private final SearchRequestConverter searchRequestConverter;

    @Override
    public List<T> search(SearchRequest request) {
        Pageable pageable = searchRequestConverter.getPageable(request);
        Specification<T> specification = searchRequestConverter.getSpecification(request);

        List<T> result = categoryRepository.findAll(specification, pageable)
                .getContent();

        result = result.stream()
                .map(categoryConverter::fillIdIndexes)
                .map(i -> (T) i)
                .toList();

        return result;
    }

    @Override
    public T get(@NotNull Long id) {
        return (T) categoryConverter.fillIdIndexes(
                categoryRepository.findById(id).orElseThrow()
        );
    }

    @Override
    public T create(T source) {
        categoryConverter.fillIdObjects(source);

        categoryRepository.save(source);

        source = categoryRepository.findById((source).getId()).orElseThrow();

        categoryConverter.fillIdIndexes(source);

        return source;
    }

    @Override
    public T update(@NotNull Long id, T source) {
        T model = categoryRepository.findById(id).orElseThrow();

        categoryConverter.updateModel(model, source);

        categoryConverter.fillIdObjects(model);

        categoryRepository.save(model);

        model = categoryRepository.findById(id).orElseThrow();

        categoryConverter.fillIdIndexes(model);

        return model;
    }
}
