package com.ciberpet.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
        // Obtenemos la URL que el usuario intenta visitar
        String requestURI = request.getRequestURI();
        
        // Verificamos si la ruta solicitada es una ruta de administrador
        if (requestURI.startsWith("/admin")) {
            HttpSession session = request.getSession();
            
            // 1. ¿Hay alguien en la sesión?
            if (session.getAttribute("usuarioSesion") == null) {
                // Si no hay nadie logueado, lo mandamos al login
                response.sendRedirect(request.getContextPath() + "/login");
                return false; // Detenemos la ejecución
            }
            
            // 2. Si hay alguien, ¿es un Administrador?
            String rol = (String) session.getAttribute("rol");
            if (!"Administrador".equalsIgnoreCase(rol)) {
                // Si no es admin, lo mandamos a la página de inicio (acceso denegado)
                response.sendRedirect(request.getContextPath() + "/");
                return false; // Detenemos la ejecución
            }
        }
        
        // Si la ruta no es /admin o si pasó todas las validaciones de admin, dejamos que continúe.
        return true;
    }
}