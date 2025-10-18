package org.myproject.bmanager5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CommonRepository<T>
        extends JpaRepository<T, Long>,
        JpaSpecificationExecutor<T> {

}
