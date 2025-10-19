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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    protected CategoryModel parent;

    @ManyToOne
    @JoinColumn(name = "child_id")
    protected CategoryModel child;
}
