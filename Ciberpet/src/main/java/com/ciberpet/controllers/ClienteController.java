package com.ciberpet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ciberpet.dtos.ClienteFilter;
import com.ciberpet.dtos.ResultadoResponse;
import com.ciberpet.models.Usuario;
import com.ciberpet.services.ClienteService;
import com.ciberpet.utils.Alert;


@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Listado
    @GetMapping("/admin/clientes")
    public String listadoClientes(@ModelAttribute ClienteFilter filtro, Model model) {
        List<Usuario> lstClientes = clienteService.search(filtro);
		model.addAttribute("filtro", filtro);
        model.addAttribute("lstClientes", lstClientes);
        return "admin/clientes/listadoCliente";
    }


    // Cambiar estado (activo/inactivo)
    @PostMapping("/admin/clientes/cambiar-estado/{id}")
    public String cambiarEstadoCliente(@PathVariable int id, RedirectAttributes flash) {
        ResultadoResponse response = clienteService.cambiarEstado(id);
        flash.addFlashAttribute("toast", Alert.sweetToast(response.mensaje, "success", 5000));
        return "redirect:/admin/clientes";
    }
}
