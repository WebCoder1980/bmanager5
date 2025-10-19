package org.myproject.bmanager5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;

@Entity(name = "category")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
public class CategoryModel implements ModelInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    protected String name;

    @ManyToMany(cascade = ALL)
    @JoinTable(
            name = "category_hierarchy",
            joinColumns = @JoinColumn(name = "child_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id")
    )
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    protected Set<CategoryModel> parents = new HashSet<>();

    @ManyToMany(mappedBy = "parents", cascade = ALL)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    protected Set<CategoryModel> children = new HashSet<>();

    public void clearChildren() {
        for (CategoryModel child : getChildren()) {
            child.getParents().remove(this);
        }
        getChildren().clear();
    }

    // For Rest API

    @Transient
    protected Set<Long> parentsId;

    @Transient
    protected Set<Long> childrenId;
}
