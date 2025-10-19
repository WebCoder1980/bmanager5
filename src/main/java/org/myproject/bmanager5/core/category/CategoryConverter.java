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
        // parents

        var parents = model.getParents()
                .stream()
                .map(i -> i.getParent().getId())
                .collect(Collectors.toSet());

        model.setParentsId(parents);

        // children

        var children = model.getChildren()
                .stream()
                .map(i -> i.getChild().getId())
                .collect(Collectors.toSet());

        model.setChildrenId(children);

        // return

        return model;
    }

    @Transactional
    public void fillIdObjectsAndSave(CategoryModel model) {
        // delete all old models

        categoryHierarchyRepository.deleteByParentIdOrChildId(model.getId(), model.getId());

        // parents

        for (Long i : model.getParentsId()) {
            var newModel = CategoryHierarchyModel.builder()
                    .parent(
                            categoryRepository.findById(i).orElseThrow()
                    )
                    .child(model)
                    .build();

            categoryHierarchyRepository.save(newModel);
        }

        // children

        for (Long i : model.getChildrenId()) {
            var newModel = CategoryHierarchyModel.builder()
                    .parent(model)
                    .child(
                            categoryRepository.findById(i).orElseThrow()
                    )
                    .build();

            categoryHierarchyRepository.save(newModel);
        }
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
