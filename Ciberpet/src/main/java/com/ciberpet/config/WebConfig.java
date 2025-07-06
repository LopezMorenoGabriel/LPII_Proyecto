package com.ciberpet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ciberpet.interceptors.AdminInterceptor;
import com.ciberpet.interceptors.ClienteInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AdminInterceptor adminInterceptor;

    @Autowired
    private ClienteInterceptor clienteInterceptor;

    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Aquí le decimos a Spring que use nuestro interceptor
        registry.addInterceptor(adminInterceptor)
                // Y que lo aplique SOLO a las rutas que comiencen con /admin
                .addPathPatterns("/admin/**");

        registry.addInterceptor(clienteInterceptor)
                .addPathPatterns("/procesarPedido", "/carrito/**");
    }
}