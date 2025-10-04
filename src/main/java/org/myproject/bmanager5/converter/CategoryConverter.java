package org.myproject.bmanager5.converter;

import lombok.AllArgsConstructor;
import org.myproject.bmanager5.dto.response.CategoryDTO;
import org.myproject.bmanager5.dto.tmpdto.CategoryTmpDTO;
import org.myproject.bmanager5.dto.viewdto.CategoryViewDTO;
import org.myproject.bmanager5.model.CategoryModel;
import org.myproject.bmanager5.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
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
                .setParents(new ArrayList<>(
                        dto.getParentsId()
                                .stream()
                                .map(i -> categoryRepository.findById(i).orElseThrow())
                                .toList()
                ))
                .setChildren(new ArrayList<>(
                        dto.getChildrenId()
                                .stream()
                                .map(i -> categoryRepository.findById(i).orElseThrow())
                                .toList()
                ));
    }

    public CategoryViewDTO tmpDTOToViewDTO(CategoryTmpDTO source) {
        return CategoryViewDTO.builder()
                .id(source.getId())
                .name(source.getName())
                .parentsId(
                        Optional.ofNullable(source.getParentsIdArray())
                                .map(i -> Arrays.stream(i).collect(Collectors.toSet()))
                                .orElse(Set.of())
                )
                .childrenId(
                        Optional.ofNullable(source.getChildrenIdArray())
                                .map(i -> Arrays.stream(i).collect(Collectors.toSet()))
                                .orElse(Set.of())
                )
                .path(source.getPath())
                .build();
    }
}
