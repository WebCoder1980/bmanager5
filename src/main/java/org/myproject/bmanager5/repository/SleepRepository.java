package org.myproject.bmanager5.repository;

import org.myproject.bmanager5.model.SleepModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SleepRepository extends JpaRepository<SleepModel, Long> {

}
