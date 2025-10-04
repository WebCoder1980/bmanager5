package org.myproject.bmanager5.repository;

import org.myproject.bmanager5.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {

}
