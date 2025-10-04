package org.myproject.bmanager5.service;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class AppFileReader {
    @SneakyThrows
    public String getSQL(String path) {
        final String fullPath = "sql/" + path;
        return new String(
                new ClassPathResource(fullPath)
                        .getInputStream()
                        .readAllBytes(),
                StandardCharsets.UTF_8
        );
    }
}
