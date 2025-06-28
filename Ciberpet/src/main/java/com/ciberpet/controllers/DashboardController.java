package com.ciberpet.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        // Validar sesión
        Object usuarioSesion = session.getAttribute("usuarioSesion");
        String rol = (String) session.getAttribute("rol");

        if (usuarioSesion == null) {
            return "redirect:/login";
        }

        if (!"Administrador".equalsIgnoreCase(rol)) {
            return "redirect:/";
        }

        return "dashboard/dashboard"; 
    }
}
