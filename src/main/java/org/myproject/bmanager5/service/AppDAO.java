package org.myproject.bmanager5.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class AppDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @SneakyThrows
    private String readFile(String path) {
        return new String(
                new ClassPathResource(path)
                        .getInputStream()
                        .readAllBytes(),
                StandardCharsets.UTF_8
        );
    }

    public String getSQL(String path, String pageable) {
        return String.format(
                "%s\n%s",
                readFile(path),
                pageable
        );
    }
}
