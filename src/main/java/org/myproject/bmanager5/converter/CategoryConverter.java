package org.myproject.bmanager5.converter;

import lombok.AllArgsConstructor;
import org.myproject.bmanager5.model.CategoryModel;
import org.myproject.bmanager5.repository.CommonRepository;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CategoryConverter<T extends CategoryModel> {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final CommonRepository<T> categoryRepository;

    public CategoryModel fillIdIndexes(CategoryModel model) {
        List<Field> allFields = List.of(model.getClass().getDeclaredFields()).stream()
                .peek(i -> i.setAccessible(true))
                .toList();

        List<Field> indexFields = allFields.stream()
                .filter(i -> i.getName().endsWith("Id"))
                .toList();
        final String ID_SUFFIX = "Id";
        Set<String> fieldsNames = indexFields.stream()
                .map(Field::getName)
                .collect(Collectors.toSet());

        allFields.stream()
                .filter(i -> fieldsNames.contains(i.getName()))
                .forEach(i -> {
                    try {
                        i.set(
                                model,
                                allFields.stream()
                                        .filter(j -> j.getName().equals(i.getName().substring(0, i.getName().length() - ID_SUFFIX.length())))
                                        .findFirst()
                                        .map(j -> {
                                            Set<Long> ids;
                                            try {
                                                ids = ((Set<T>) j.get(model))
                                                        .stream()
                                                        .map(T::getId)
                                                        .collect(Collectors.toSet());

                                            } catch (Exception e) {
                                                throw new RuntimeException(e);
                                            }
                                            return ids;
                                        })
                                        .orElseThrow()
                        );
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });

        return model;
    }

    public void fillIdObjects(CategoryModel model) {
        model.setParents(
                model.getParentsId()
                        .stream()
                        .map(childId -> (CategoryModel) categoryRepository.findById(childId).orElseThrow())
                        .collect(Collectors.toSet())
        );

        model.getChildrenId()
                .stream()
                .map(parentId -> (CategoryModel) categoryRepository.findById(parentId).orElseThrow())
        .forEach(CategoryModel::clearChildren);

        model.clearChildren();

        model.getChildrenId()
                .stream()
                .map(parentId -> categoryRepository.findById(parentId).orElseThrow())
                .forEach(parent -> model.getChildren().add((CategoryModel) parent));

        model.getChildrenId()
                .stream()
                .map(parentId -> categoryRepository.findById(parentId).orElseThrow())
                .forEach(parent -> ((CategoryModel) parent).getParents().add(model));
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
