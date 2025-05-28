package com.example.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })  // A anotação será usada em nivel de classe
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReservaLabValidator.class)  // Validador que implementará a lógica
public @interface ValidReservaLab {

    String message() default "Data e hora da reserva inválidas";  // Mensagem de erro

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
