package org.myproject.bmanager5.core.categoryhierarchy;

import jakarta.validation.constraints.NotNull;
import org.myproject.bmanager5.core.common.CommonRepository;

public interface CategoryHierarchyRepository extends CommonRepository<CategoryHierarchyModel> {
    void deleteByChildId(@NotNull Long childId);

    void deleteByParentId(@NotNull Long parentId);
}
