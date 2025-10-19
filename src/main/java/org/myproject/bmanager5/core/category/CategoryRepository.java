package org.myproject.bmanager5.core.category;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CategoryRepository
        extends JpaRepository<CategoryModel, Long>,
        JpaSpecificationExecutor<CategoryModel> {
     @Override
     @EntityGraph(attributePaths = {"parents", "children"})
     @Nonnull
     List<CategoryModel> findAll(Specification<CategoryModel> spec);
}
