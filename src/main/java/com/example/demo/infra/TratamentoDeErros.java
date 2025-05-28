package com.example.demo.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratamentoDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> notFound404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValid(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors();

        return ResponseEntity.badRequest().body(errors.stream().map(DadosErros::new).toList());
    }

    public record DadosErros(String campo, String mensagem) {

        public DadosErros(FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> illegalState(IllegalStateException ex){
        var errors = ex.getMessage();

        return ResponseEntity.badRequest().body(errors);
    }
}
