package org.myproject.bmanager5.exception;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.myproject.bmanager5.dto.response.AppResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.TreeMap;
import java.util.TreeSet;

@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {
    private final Logger logger;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle(MethodArgumentNotValidException ex) {
        AppResponse<?> result = new AppResponse<>(new TreeMap<>());

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(i -> {
                    result.getErrors().computeIfAbsent(i.getField(), j -> new TreeSet<>());
                    result.getErrors().get(i.getField()).add(i.getDefaultMessage());
                });

        logger.warn("Bad request: {}", result.toString());

        return ResponseEntity.badRequest().body(result);
    }

    @ExceptionHandler(RestIllegalArgumentException.class)
    public ResponseEntity<?> handle(RestIllegalArgumentException ex) {
        logger.warn("Bad request: {} - {}", ex.getField(), ex.getMessage());

        return ResponseEntity.badRequest().body(new AppResponse<>().addErrorFluent(ex.getField(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception ex) {
        logger.warn("Bad request:", ex);

        return ResponseEntity.badRequest().body(new AppResponse<>().addErrorFluent(ex.getMessage()));
    }
}