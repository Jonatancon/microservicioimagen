package com.pragma.microservicioimagen;

import com.pragma.microservicioimagen.domain.exceptions.ConflictException;
import com.pragma.microservicioimagen.domain.exceptions.InternalExceptio;
import com.pragma.microservicioimagen.domain.exceptions.NotFountException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ExceptionConfig {
    @ExceptionHandler({ConflictException.class})
    public Mono<ResponseEntity<String>> conflictException(ConflictException conflictException) {
        return Mono.just(
                ResponseEntity.status(409)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(conflictException.getMessage())
        );
    }

    @ExceptionHandler(NotFountException.class)
    public Mono<ResponseEntity<String>> notFoundException(NotFountException notFoundException) {
        return Mono.just(
                ResponseEntity.status(404)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(notFoundException.getMessage())
        );
    }

    @ExceptionHandler(InternalExceptio.class)
    public Mono<ResponseEntity<String>> internalExceptionHandler(InternalExceptio internalExceptio) {
        return Mono.just(
                ResponseEntity.internalServerError()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(internalExceptio.getMessage())
        );
    }
}
