package com.ciberpet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ciberpet.models.Usuario;
import com.ciberpet.services.ClienteService;


@Controller
public class ClienteController {
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/listadoCliente")
	public String listadoCliente(Model model) {
		List<Usuario> lstClientes = clienteService.getAll();
		model.addAttribute("lstClientes", lstClientes);
		return "dashboard/clientes/listadoCliente";
	}
}
