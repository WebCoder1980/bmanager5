package org.myproject.bmanager5.controller;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.myproject.bmanager5.dto.request.SearchRequest;
import org.myproject.bmanager5.dto.response.AppResponse;
import org.myproject.bmanager5.service.GatewayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/{entity}")
@AllArgsConstructor
public class CategoryController {
    private final GatewayService gatewayService;

    @PostMapping("/search")
    public ResponseEntity<AppResponse<List<Object>>> search(
            @PathVariable String entity,
            @RequestBody SearchRequest body
    ) {
        return ResponseEntity.ok()
                .body(new AppResponse<>(gatewayService.search(entity, body)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppResponse<Object>> get(
            @PathVariable String entity,
            @PathVariable @NotNull Long id
    ) {
        return ResponseEntity.ok()
                .body(new AppResponse<>(gatewayService.get(entity, id)));
    }

    @PostMapping
    public ResponseEntity<AppResponse<Object>> create(
            @PathVariable String entity,
            @RequestBody Object request
    ) {
        return ResponseEntity.ok()
                .body(new AppResponse<>(gatewayService.create(entity, request)));
    }

    @PutMapping("{id}")
    public ResponseEntity<AppResponse<Object>> update(
            @PathVariable String entity,
            @PathVariable @NotNull Long id,
            @RequestBody Object request
    ) {
        return ResponseEntity.ok()
                .body(new AppResponse<>(gatewayService.update(entity, id, request)));
    }
}
