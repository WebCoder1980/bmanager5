package org.myproject.bmanager5.core.common;

import lombok.AllArgsConstructor;
import org.myproject.bmanager5.core.category.CategoryRepository;
import org.myproject.bmanager5.core.categoryhierarchy.CategoryHierarchyRepository;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public abstract class AbstractConverter<T, TR> {
    protected final CategoryRepository categoryRepository;
    protected final CategoryHierarchyRepository categoryHierarchyRepository;
}
