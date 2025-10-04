package org.myproject.bmanager5.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

@Component
public class AppEntityManager {
    @PersistenceContext
    private EntityManager entityManager;
}
