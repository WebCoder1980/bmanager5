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
        var builder = CategoryDTO.builder();

        builder.id(model.getId())
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
                );

        return builder.build();
    }

    public CategoryModel dtoToModel(CategoryDTO dto) {
        var builder = CategoryModel.builder();

        builder.id(dto.getId())
                .name(dto.getName())
                .parents(new HashSet<>(
                        dto.getParentsId()
                                .stream()
                                .map(i -> categoryRepository.findById(i).orElseThrow())
                                .collect(Collectors.toSet())
                ))
                .children(new HashSet<>(
                        dto.getChildrenId()
                                .stream()
                                .map(i -> categoryRepository.findById(i).orElseThrow())
                                .collect(Collectors.toSet())
                ));

        return builder.build();
    }

    public void enrichModel(CategoryModel model, CategoryDTO dto) {
        if (dto.getName() != null) {
            model.setName(dto.getName());
        }

        if (dto.getParentsId() != null) {
            model.setParents(
                    dto.getParentsId()
                            .stream()
                            .map(i -> categoryRepository.findById(i).orElseThrow())
                            .collect(Collectors.toSet())
            );
        }

        if (dto.getChildrenId() != null) {
            model.clearChildren();

            model.getChildren().addAll(
                    dto.getChildrenId()
                            .stream()
                            .map(childId -> categoryRepository.findById(childId).orElseThrow())
                            .collect(Collectors.toSet())
            );
        }
    }
}
