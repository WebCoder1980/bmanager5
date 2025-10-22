package org.myproject.bmanager5.core.categoryhierarchy;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.myproject.bmanager5.core.category.CategoryModel;

@Entity(name = "category_hierarchy")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
public class CategoryHierarchyModel {
    @EmbeddedId
    protected CategoryHierarchyEmbeddedId id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @MapsId("parentId")
    protected CategoryModel parent;

    @ManyToOne
    @JoinColumn(name = "child_id")
    @MapsId("childId")
    protected CategoryModel child;
}
