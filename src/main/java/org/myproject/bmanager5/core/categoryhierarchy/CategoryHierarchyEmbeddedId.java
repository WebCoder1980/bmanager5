package org.myproject.bmanager5.core.categoryhierarchy;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class CategoryHierarchyEmbeddedId implements Serializable {
    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "child_id")
    private Long childId;
}
