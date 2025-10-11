package org.myproject.bmanager5.service;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.myproject.bmanager5.converter.CategoryConverter;
import org.myproject.bmanager5.converter.SearchRequestConverter;
import org.myproject.bmanager5.dto.request.SearchRequest;
import org.myproject.bmanager5.dto.response.CategoryDTO;
import org.myproject.bmanager5.model.CategoryModel;
import org.myproject.bmanager5.repository.CategoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;
    private final SearchRequestConverter searchRequestConverter;

    public List<CategoryDTO> search(SearchRequest request) {
        Pageable pageable = searchRequestConverter.getPageable(request);
        Specification<CategoryModel> specification = searchRequestConverter.getSpecification(request);

        return categoryRepository.findAll(specification, pageable)
                .stream()
                .map(categoryConverter::modelToDTO)
                .toList();
    }

    public CategoryDTO get(@NotNull Long id) {
        return categoryConverter.modelToDTO(
                categoryRepository.findById(id).orElseThrow()
        );
    }

    public CategoryDTO create(CategoryDTO dto) {
        CategoryModel model = categoryConverter.dtoToModel(dto);
        categoryRepository.save(model);
        return categoryConverter.modelToDTO(model);
    }

    public CategoryDTO update(@NotNull Long id, CategoryDTO dto) {
        CategoryModel model = categoryRepository.findById(id).orElseThrow();

        categoryConverter.enrichModel(model, dto);

        categoryRepository.save(model);

        model = categoryRepository.findById(id).orElseThrow();

        return categoryConverter.modelToDTO(model);
    }
}
