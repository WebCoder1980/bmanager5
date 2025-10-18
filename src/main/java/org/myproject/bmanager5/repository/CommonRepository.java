package org.myproject.bmanager5.repository;

import org.myproject.bmanager5.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommonRepository
        extends JpaRepository<CategoryModel, Long>,
        JpaSpecificationExecutor<CategoryModel> {

}
