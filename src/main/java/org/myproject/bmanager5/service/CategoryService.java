package org.myproject.bmanager5.service;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.myproject.bmanager5.converter.ModelDTOConverter;
import org.myproject.bmanager5.converter.PageableConverter;
import org.myproject.bmanager5.dto.response.CategoryDTO;
import org.myproject.bmanager5.dto.response.CategoryWithPathDTO;
import org.myproject.bmanager5.dto.response.PathDTO;
import org.myproject.bmanager5.model.CategoryModel;
import org.myproject.bmanager5.repository.CategoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelDTOConverter modelDTOConverter;
    private final PageableConverter pageableConverter;

    public List<CategoryDTO> getAll(Integer start, Integer size, String sortBy, String sortDirection) {
        Pageable pageable = pageableConverter.toPageable(start, size, sortBy, sortDirection);

        return categoryRepository.findAll(pageable)
                .stream()
                .map(modelDTOConverter::categoryModelToDTO)
                .toList();
    }

    public CategoryDTO get(@NotNull Long id) {
        return modelDTOConverter.categoryModelToDTO(
                categoryRepository.findById(id).orElseThrow()
        );
    }

    public List<CategoryWithPathDTO> getAllWithPath(Integer start, Integer size, String sortBy, String sortDirection) {
        List<PathDTO> paths = categoryRepository.findAllWithPath();

        Pageable pageable = pageableConverter.toPageable(start, size, sortBy, sortDirection);

        return categoryRepository.findAll(pageable)
                .stream()
                .map(i -> modelDTOConverter.categoryDTOToCategoryWithPathDTO(
                        modelDTOConverter.categoryModelToDTO(i),
                        paths.stream()
                                .filter(j -> j.getId().equals(i.getId()))
                                .toList()
                ))
                .toList();
    }

    public CategoryDTO create(CategoryDTO dto) {
        CategoryModel model = modelDTOConverter.categoryDTOToModel(dto);
        categoryRepository.save(model);
        return modelDTOConverter.categoryModelToDTO(model);
    }

    public CategoryDTO update(@NotNull Long id, CategoryDTO dto) {
        CategoryModel model = categoryRepository.findById(id).orElseThrow();

        if (dto.getName() != null) {
            model.setName(dto.getName());
        }
        if (dto.getParentsId() != null) {
            model.setParents(
                    dto.getParentsId()
                            .stream()
                            .map(i -> categoryRepository.findById(i).orElseThrow())
                            .toList()
            );
        }

        categoryRepository.save(model);

        return modelDTOConverter.categoryModelToDTO(model);
    }
}
