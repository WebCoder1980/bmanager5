package org.myproject.bmanager5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany
    @JoinTable(
            name = "category_hierarchy",
            joinColumns = @JoinColumn(name = "child_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id")
    )
    @JsonIgnore
    protected List<CategoryModel> parents = new ArrayList<>();

    @ManyToMany(mappedBy = "parents")
    @JsonIgnore
    protected List<CategoryModel> children = new ArrayList<>();
}
