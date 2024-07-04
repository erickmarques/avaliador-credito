package com.erickmarques.ms_avaliador_credito.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;

import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {

        BindingResult bindingResult = ex.getBindingResult();
        List<String> erros = bindingResult.getAllErrors()
                .stream()
                .map(obj -> obj.getDefaultMessage())
                .collect(Collectors.toList());

        return new ApiErrors(LocalDateTime.now().toString(), request.getRequestURI(), erros);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrors> handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request){
        String mensagemErro = ex.getReason();
        HttpStatusCode codigoStatus = ex.getStatusCode();
        ApiErrors apiErrors = new ApiErrors(LocalDateTime.now().toString(), request.getRequestURI(), mensagemErro);
        return new ResponseEntity<>(apiErrors, codigoStatus);
    }
}
