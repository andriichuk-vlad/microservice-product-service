package com.example_microservice.product_service.exception;

import org.jspecify.annotations.Nullable;
import org.springframework.http.*;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

@RestControllerAdvice
public class ProductServiceCustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ProblemDetail problemDetail = handleValidationException(ex, status);
        return ResponseEntity.status(status.value()).body(problemDetail);
    }

    private ProblemDetail handleValidationException(MethodArgumentNotValidException ex, HttpStatusCode status) {
        String details = getErrorDetails(ex);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, details);
        problemDetail.setType(URI.create("http://localhost:8081/errors/bad-request"));
        problemDetail.setTitle("Bad Request");
        problemDetail.setInstance(ex.getBody().getInstance());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    private String getErrorDetails(MethodArgumentNotValidException ex) {
        return Optional.of(ex.getDetailMessageArguments())
                .map(args -> Arrays.stream(args)
                        .filter(msg -> !ObjectUtils.isEmpty(msg))
                        .reduce("Please make sure to provide a valid request, ",
                                (a, b) -> a + " " + b)
                )
                .orElse("")
                .toString();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(
            ProductNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String requestUriEndpoint = request.getDescription(false);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problemDetail.setType(URI.create("http://localhost:8081/errors/not-found"));
        problemDetail.setTitle("Product Not Found");
        problemDetail.setInstance(URI.create(requestUriEndpoint.replace("uri=", "")));
        problemDetail.setProperty("timestamp", Instant.now());
        return ResponseEntity.status(status).body(problemDetail);
    }
}
