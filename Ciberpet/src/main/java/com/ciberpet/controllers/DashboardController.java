package com.ciberpet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ciberpet.services.CitaService;
import com.ciberpet.services.ClienteService;
import com.ciberpet.services.ProductoService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class DashboardController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ClienteService clienteService;


    @GetMapping("/admin/dashboard")
    public String dashboard(Model model, HttpServletRequest request) {
        model.addAttribute("currentUri", request.getRequestURI());

        long nuevasCitas = citaService.countCitasDelMes();
        long nuevosProductos = productoService.countProductosDelMes();
        long nuevosClientes = clienteService.countClientesDelMes();
        double ingresosDelMes = citaService.calcularIngresosDelMes();

        model.addAttribute("nuevasCitas", nuevasCitas);
        model.addAttribute("nuevosProductos", nuevosProductos);
        model.addAttribute("nuevosClientes", nuevosClientes);
        model.addAttribute("ingresosDelMes", ingresosDelMes);
        model.addAttribute("ventassDelMes", ingresosDelMes);

        return "admin/inicio/dashboard";
    }
}
