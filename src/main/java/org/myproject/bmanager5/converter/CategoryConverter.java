package org.myproject.bmanager5.converter;

import lombok.AllArgsConstructor;
import org.myproject.bmanager5.dto.response.CategoryDTO;
import org.myproject.bmanager5.dto.response.CategoryWithPathDTO;
import org.myproject.bmanager5.dto.response.PathDTO;
import org.myproject.bmanager5.model.CategoryModel;
import org.myproject.bmanager5.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
                ));
    }

    public CategoryWithPathDTO dtoToWithPathDTO(CategoryDTO source, List<PathDTO> paths) {
        return CategoryWithPathDTO.builder()
                .id(source.getId())
                .name(source.getName())
                .parentsId(source.getParentsId())
                .paths(paths.stream()
                        .map(PathDTO::getFullPath)
                        .toList())
                .build();
    }
}
