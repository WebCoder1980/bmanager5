package org.myproject.bmanager5.core.category;

import lombok.AllArgsConstructor;
import org.myproject.bmanager5.core.categoryhierarchy.CategoryHierarchyModel;
import org.myproject.bmanager5.core.categoryhierarchy.CategoryHierarchyRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CategoryConverter {
    private final CategoryRepository categoryRepository;
    private final CategoryHierarchyRepository categoryHierarchyRepository;

    public CategoryModel fillIdIndexes(CategoryModel model) {
        model.setParentsId(
                model.getParents()
                        .stream()
                        .map(i -> i.getParent().getId())
                        .collect(Collectors.toSet())
        );

        model.setChildrenId(
                model.getChildren()
                        .stream()
                        .map(i -> i.getChild().getId())
                        .collect(Collectors.toSet())
        );

        return model;
    }

    @Transactional
    public void fillIdObjects(CategoryModel model) {
        categoryHierarchyRepository.deleteByParentId(model.getId());

        categoryHierarchyRepository.saveAll(
                model.getParentsId()
                        .stream()
                        .map(i -> {
                            return CategoryHierarchyModel.builder()
                                    .parent(
                                            categoryRepository.findById(i).orElseThrow()
                                    )
                                    .child(model)
                                    .build();
                        })
                        .toList()
        );

        categoryHierarchyRepository.deleteByChildId(model.getId());

        categoryHierarchyRepository.saveAll(
                model.getChildrenId()
                        .stream()
                        .map(i -> {
                            return CategoryHierarchyModel.builder()
                                    .parent(model)
                                    .child(
                                            categoryRepository.findById(i).orElseThrow()
                                    )
                                    .build();
                        })
                        .toList()
        );
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
