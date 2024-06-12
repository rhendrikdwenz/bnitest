package com.bni.test.BNI.Controller;

import com.bni.test.BNI.Model.Response.WebResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException e){
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.valueOf(e.getStatusCode().value()).getReasonPhrase())
                .message(e.getReason())
                .build();
        return ResponseEntity
                .status(e.getStatusCode())
                .body(response);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        String customMessage = "There was a violation of data integrity. Please check your input.";

        // Memuat pesan kesalahan yang disesuaikan ke dalam respon
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(customMessage)
                .build();

        // Mengembalikan respons dengan pesan kesalahan yang disesuaikan
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // untuk menangani error validation
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException e) {
        Map<String, Object> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(
                err -> errors.put(err.getField(), err.getDefaultMessage())
        );

        WebResponse<Map<String, Object>> response = WebResponse.<Map<String, Object>>builder()
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Validation failed")
                .data(errors)
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        // Memuat pesan kesalahan yang disesuaikan ke dalam respon
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(e.getMessage())
                .build();

        // Mengembalikan respons dengan pesan kesalahan yang disesuaikan
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}