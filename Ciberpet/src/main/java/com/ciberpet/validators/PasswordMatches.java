package com.ciberpet.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE}) // Se aplicará a nivel de clase
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class) // La lógica estará en esta clase
@Documented
public @interface PasswordMatches {
    // Mensaje de error por defecto
    String message() default "Las contraseñas no coinciden";
    
    // Grupos y payload, estándar de las anotaciones de validación
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}