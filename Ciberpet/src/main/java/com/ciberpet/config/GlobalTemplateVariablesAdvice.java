package com.ciberpet.config; // O tu paquete de configuración

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalTemplateVariablesAdvice {

    @ModelAttribute("currentUri")
    public String addCurrentUriToModel(HttpServletRequest request) {
        return request.getRequestURI();
    }
}