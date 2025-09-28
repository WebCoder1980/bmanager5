package org.myproject.bmanager5.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "category")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "category_hierarchy",
            joinColumns = @JoinColumn(name = "child_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id")
    )
    private List<CategoryModel> parents = new ArrayList<>();

    @ManyToMany(mappedBy = "parents")
    private List<CategoryModel> childs = new ArrayList<>();
}
