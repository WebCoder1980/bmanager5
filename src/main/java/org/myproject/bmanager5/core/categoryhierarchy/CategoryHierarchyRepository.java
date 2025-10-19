package org.myproject.bmanager5.core.categoryhierarchy;

import jakarta.validation.constraints.NotNull;
import org.myproject.bmanager5.core.common.CommonRepository;

public interface CategoryHierarchyRepository extends CommonRepository<CategoryHierarchyModel> {
    void deleteByParentIdOrChildId(@NotNull Long parentId, @NotNull Long childId);
}
