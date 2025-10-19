package org.myproject.bmanager5.converter;

import lombok.AllArgsConstructor;
import org.myproject.bmanager5.model.CategoryModel;
import org.myproject.bmanager5.model.ModelInterface;
import org.myproject.bmanager5.repository.CommonRepository;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class CommonConverter<T extends ModelInterface, TR extends CommonRepository<T>> {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final TR categoryRepository;

    public T fillIdIndexes(T model) {
        final String ID_SUFFIX = "Id";

        List<Field> allFields = Stream.of(model.getClass().getDeclaredFields())
                .peek(i -> i.setAccessible(true))
                .toList();

        List<Field> indexFields = allFields.stream()
                .filter(i -> i.getName().endsWith("Id"))
                .toList();

        Set<String> fieldsNames = indexFields.stream()
                .map(Field::getName)
                .collect(Collectors.toSet());

        for (var fieldTo : allFields) {
            if (!fieldsNames.contains(fieldTo.getName())) {
                continue;
            }

            Field fieldFrom = null;

            for (var i : allFields) {
                final String fieldFromName = i.getName();
                final String fieldToName = fieldTo.getName().substring(0, fieldTo.getName().length() - ID_SUFFIX.length());

                if (fieldFromName.equals(fieldToName)) {
                    fieldFrom = i;
                    break;
                }
            }

            if (fieldFrom == null) {
                throw new RuntimeException(String.format("Field %s not found", fieldTo.getName()));
            }

            try {
                //noinspection unchecked
                Set<Long> idsSet = ((Set<T>) fieldFrom.get(model))
                        .stream()
                        .map(T::getId)
                        .collect(Collectors.toSet());

                fieldTo.set(model, idsSet);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

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
        .forEach(this::clearChildren);

        clearChildren(model);

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

    private void clearChildren(CategoryModel model) {
        for (CategoryModel child : model.getChildren()) {
            child.getParents().remove(model);
        }
        model.getChildren().clear();
    }
}
