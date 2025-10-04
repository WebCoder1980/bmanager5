package org.myproject.bmanager5.service;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.myproject.bmanager5.converter.CategoryConverter;
import org.myproject.bmanager5.converter.PageableConverter;
import org.myproject.bmanager5.dto.response.CategoryDTO;
import org.myproject.bmanager5.dto.viewdto.CategoryViewDTO;
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
    private final CategoryConverter categoryConverter;
    private final PageableConverter pageableConverter;

    public List<CategoryDTO> getAll(Integer start, Integer size, String sortBy, String sortDirection) {
        Pageable pageable = pageableConverter.toPageable(start, size, sortBy, sortDirection);

        return categoryRepository.findAll(pageable)
                .stream()
                .map(categoryConverter::modelToDTO)
                .toList();
    }

    public CategoryDTO get(@NotNull Long id) {
        return categoryConverter.modelToDTO(
                categoryRepository.findById(id).orElseThrow()
        );
    }

    public List<CategoryViewDTO> getAllWithPath(Integer start, Integer size, String sortBy, String sortDirection) {
        List<PathDTO> paths = categoryRepository.findAllWithPath();

        Pageable pageable = pageableConverter.toPageable(start, size, sortBy, sortDirection);

        return categoryRepository.findAll(pageable)
                .stream()
                .map(i -> categoryConverter.dtoToViewDTO(
                        categoryConverter.modelToDTO(i),
                        paths.stream()
                                .filter(j -> j.getId().equals(i.getId()))
                                .toList()
                ))
                .toList();
    }

    public CategoryDTO create(CategoryDTO dto) {
        CategoryModel model = categoryConverter.dtoToModel(dto);
        categoryRepository.save(model);
        return categoryConverter.modelToDTO(model);
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

        return categoryConverter.modelToDTO(model);
    }
}
