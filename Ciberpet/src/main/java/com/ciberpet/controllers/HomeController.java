package com.ciberpet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ciberpet.services.ProductoService;

@Controller
public class HomeController {

	@Autowired
	private ProductoService _productoService;

	@GetMapping({"/", "/inicio"})
	public String mostrarInicio() {
		return "inicio/inicio";
	}

	@GetMapping("/nosotros")
	public String mostrarNosotros() {
		return "inicio/nosotros";
	}

	@GetMapping("/contacto")
	public String mostrarContacto() {
		return "inicio/contacto";
	}

	@GetMapping("/productos")
	public String mostrarProductos(Model model) {
		model.addAttribute("productos", _productoService.lstProductos());
		model.addAttribute("lstPerros", _productoService.findByCategoriaDescripcion("Perros"));
	    model.addAttribute("lstGatos", _productoService.findByCategoriaDescripcion("Gatos"));
	    model.addAttribute("lstAccesorios", _productoService.findByCategoriaDescripcion("Accesorios"));
		return "inicio/productos";
	}
}
