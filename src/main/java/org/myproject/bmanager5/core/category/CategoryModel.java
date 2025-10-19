package org.myproject.bmanager5.core.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.myproject.bmanager5.core.categoryhierarchy.CategoryHierarchyModel;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;

@Entity(name = "category")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
public class CategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    protected String name;

    @OneToMany(mappedBy = "child", cascade = ALL)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    protected Set<CategoryHierarchyModel> parents = new HashSet<>();

    @OneToMany(mappedBy = "parent", cascade = ALL)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    protected Set<CategoryHierarchyModel> children = new HashSet<>();

    // For Rest API

    @Transient
    protected Set<Long> parentsId;

    @Transient
    protected Set<Long> childrenId;
}
