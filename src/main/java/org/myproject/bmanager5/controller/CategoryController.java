package org.myproject.bmanager5.controller;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.myproject.bmanager5.dto.request.SearchRequest;
import org.myproject.bmanager5.dto.response.AppResponse;
import org.myproject.bmanager5.model.CategoryModel;
import org.myproject.bmanager5.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/search")
    public ResponseEntity<AppResponse<List<CategoryModel>>> search(@RequestBody SearchRequest body) {
        return ResponseEntity.ok()
                .body(new AppResponse<>(categoryService.search(body)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppResponse<CategoryModel>> get(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok()
                .body(new AppResponse<>(categoryService.get(id)));
    }

    @PostMapping
    public ResponseEntity<AppResponse<CategoryModel>> create(@RequestBody CategoryModel request) {
        return ResponseEntity.ok()
                .body(new AppResponse<>(categoryService.create(request)));
    }

    @PutMapping("{id}")
    public ResponseEntity<AppResponse<CategoryModel>> update(
            @PathVariable @NotNull Long id,
            @RequestBody CategoryModel request
    ) {
        return ResponseEntity.ok()
                .body(new AppResponse<>(categoryService.update(id, request)));
    }
}
