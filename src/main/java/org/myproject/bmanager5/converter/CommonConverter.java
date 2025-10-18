package org.myproject.bmanager5.converter;

import lombok.AllArgsConstructor;
import org.myproject.bmanager5.model.CategoryModel;
import org.myproject.bmanager5.repository.CommonRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CommonConverter {
    private final CommonRepository commonRepository;

    public CategoryModel fillIdIndexes(CategoryModel model) {
        model.setParentsId(
                model.getParents()
                        .stream()
                        .map(CategoryModel::getId)
                        .collect(Collectors.toSet())
        );

        model.setChildrenId(
                model.getChildren()
                        .stream()
                        .map(CategoryModel::getId)
                        .collect(Collectors.toSet())
        );

        return model;
    }

    public void fillIdObjects(CategoryModel model) {
        model.setParents(
                model.getParentsId()
                        .stream()
                        .map(childId -> commonRepository.findById(childId).orElseThrow())
                        .collect(Collectors.toSet())
        );

        model.getChildrenId()
                .stream()
                .map(parentId -> commonRepository.findById(parentId).orElseThrow())
        .forEach(CategoryModel::clearChildren);

        model.clearChildren();

        model.getChildrenId()
                .stream()
                .map(parentId -> commonRepository.findById(parentId).orElseThrow())
                .forEach(parent -> model.getChildren().add(parent));

        model.getChildrenId()
                .stream()
                .map(parentId -> commonRepository.findById(parentId).orElseThrow())
                .forEach(parent -> parent.getParents().add(model));
    }

    public void updateModel(CategoryModel model, CategoryModel changesModel) {
        if (changesModel.getName() != null) {
            model.setName(changesModel.getName());
        }

        if (changesModel.getParentsId() != null) {
            model.setParentsId(
                    changesModel.getParentsId()
            );
        }

        if (changesModel.getChildrenId() != null) {
            if (model.getChildrenId() == null) {
                model.setChildrenId(new HashSet<>());
            }

            model.getChildrenId().addAll(
                    changesModel.getChildrenId()
            );
        }
    }
}
