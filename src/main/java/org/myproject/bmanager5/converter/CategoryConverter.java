package org.myproject.bmanager5.converter;

import lombok.AllArgsConstructor;
import org.myproject.bmanager5.dto.response.CategoryDTO;
import org.myproject.bmanager5.model.CategoryModel;
import org.myproject.bmanager5.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CategoryConverter {
    private final CategoryRepository categoryRepository;

    public CategoryDTO modelToDTO(CategoryModel model) {
        return CategoryDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .parentsId(
                        model.getParents()
                                .stream()
                                .map(CategoryModel::getId)
                                .collect(Collectors.toSet())
                )
                .childrenId(
                        model.getChildren()
                                .stream()
                                .map(CategoryModel::getId)
                                .collect(Collectors.toSet())
                )
                .build();
    }

    public CategoryModel dtoToModel(CategoryDTO dto) {
        return new CategoryModel()
                .setId(dto.getId())
                .setName(dto.getName())
                .setParents(new HashSet<>(
                        dto.getParentsId()
                                .stream()
                                .map(i -> categoryRepository.findById(i).orElseThrow())
                                .collect(Collectors.toSet())
                ))
                .setChildren(new HashSet<>(
                        dto.getChildrenId()
                                .stream()
                                .map(i -> categoryRepository.findById(i).orElseThrow())
                                .collect(Collectors.toSet())
                ));
    }
}
