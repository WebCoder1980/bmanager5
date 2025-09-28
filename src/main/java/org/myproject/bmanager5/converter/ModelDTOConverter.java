package org.myproject.bmanager5.converter;

import lombok.AllArgsConstructor;
import org.myproject.bmanager5.dto.response.CategoryDTO;
import org.myproject.bmanager5.model.CategoryModel;
import org.myproject.bmanager5.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ModelDTOConverter {
    private final CategoryRepository categoryRepository;

    public CategoryDTO categoryModelToDTO(CategoryModel model) {
        return new CategoryDTO()
                .setId(model.getId())
                .setName(model.getName())
                .setParentsId(
                        model.getParents()
                                .stream()
                                .map(CategoryModel::getId)
                                .collect(Collectors.toSet())
                );
    }

    public CategoryModel categoryDTOToModel(CategoryDTO dto) {
        return new CategoryModel()
                .setId(dto.getId())
                .setName(dto.getName())
                .setParents(new ArrayList<>(
                        dto.getParentsId()
                                .stream()
                                .map(i -> categoryRepository.findById(i).orElseThrow())
                                .toList()
                ));
    }
}
