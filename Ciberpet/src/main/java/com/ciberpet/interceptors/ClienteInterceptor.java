package com.ciberpet.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class ClienteInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession();
        if (session.getAttribute("usuarioSesion") == null) {
            response.sendRedirect(request.getContextPath() + "/login?origen=" + request.getRequestURI());
            return false;
        }

        return true; // Usuario autenticado, permitir acceso
    }
}
