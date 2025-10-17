package org.myproject.bmanager5.service;

import jakarta.validation.constraints.NotNull;
import org.myproject.bmanager5.dto.request.SearchRequest;
import org.myproject.bmanager5.model.CategoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GatewayService {
    private final Map<String, ServiceInterface> services;

    @Autowired
    public GatewayService(CategoryService categoryService) {
        services = Map.of(
                "category", categoryService
        );
    }

    public List<Object> search(String entity, SearchRequest request) {
        return getService(entity).search(request)
                .stream()
                .map(i -> (Object) i)
                .toList();
    }

    public Object get(String entity, @NotNull Long id) {
        return getService(entity).get(id);
    }

    public Object create(String entity, Object source) {
        return getService(entity).create((CategoryModel) source);
    }

    public Object update(String entity, @NotNull Long id, Object source) {
        return getService(entity).update(id, (CategoryModel) source);
    }

    private ServiceInterface getService(String entity) {
        ServiceInterface result = services.getOrDefault(entity, null);

        if (result == null) {
            throw new IllegalArgumentException(String.format(
                    "Entity '%s' not supported",
                    entity
            ));
        }
        return result;
    }
}
