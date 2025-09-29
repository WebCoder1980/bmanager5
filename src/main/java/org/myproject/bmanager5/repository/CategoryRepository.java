package org.myproject.bmanager5.repository;

import org.myproject.bmanager5.dto.response.PathDTO;
import org.myproject.bmanager5.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
    @Query(nativeQuery = true, value = """
            WITH RECURSIVE category_paths AS (
            SELECT
                c.id,
                c.name,
                c.name::text AS path
            FROM category c
            LEFT JOIN category_hierarchy ch ON c.id = ch.child_id
            WHERE ch.parent_id IS NULL
        
            UNION ALL
        
            SELECT
                c.id,
                c.name,
                (cp.path || ' -> ' || c.name)::text AS path
            FROM category c
            JOIN category_hierarchy ch ON c.id = ch.child_id
            JOIN category_paths cp ON ch.parent_id = cp.id
        )
        SELECT id, path
        FROM category_paths
        ORDER BY id
        """)
    List<PathDTO> findAllWithPath();
}
