package com.ciberpet.validators;

import com.ciberpet.dtos.RegistroUsuarioDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        RegistroUsuarioDTO usuarioDto = (RegistroUsuarioDTO) obj;

        if (usuarioDto.getContrasena() == null || usuarioDto.getContrasenaRepetir() == null) {
            return false;
        }

        boolean isValid = usuarioDto.getContrasena().equals(usuarioDto.getContrasenaRepetir());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Las contraseñas no coinciden")
                   .addPropertyNode("contrasenaRepetir")
                   .addConstraintViolation();
        }

        return isValid;
    }
}