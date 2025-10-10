package org.myproject.bmanager5.repository;

import jakarta.annotation.Nonnull;
import org.myproject.bmanager5.model.CategoryModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
    @Override
    @EntityGraph(attributePaths = {"parents", "children"})
    @Nonnull
    Page<CategoryModel> findAll(@Nonnull Pageable pageable);
}
