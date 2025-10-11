package org.myproject.bmanager5.controller;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.myproject.bmanager5.dto.request.SearchRequest;
import org.myproject.bmanager5.dto.response.AppResponse;
import org.myproject.bmanager5.dto.response.CategoryDTO;
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
    public ResponseEntity<AppResponse<List<CategoryDTO>>> search(@RequestBody SearchRequest body) {
        return ResponseEntity.ok()
                .body(new AppResponse<>(categoryService.search(body)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppResponse<CategoryDTO>> get(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok()
                .body(new AppResponse<>(categoryService.get(id)));
    }

    @PostMapping
    public ResponseEntity<AppResponse<CategoryDTO>> create(@RequestBody CategoryDTO dto) {
        return ResponseEntity.ok()
                .body(new AppResponse<>(categoryService.create(dto)));
    }

    @PutMapping("{id}")
    public ResponseEntity<AppResponse<CategoryDTO>> update(
            @PathVariable @NotNull Long id,
            @RequestBody CategoryDTO dto
    ) {
        return ResponseEntity.ok()
                .body(new AppResponse<>(categoryService.update(id, dto)));
    }
}
