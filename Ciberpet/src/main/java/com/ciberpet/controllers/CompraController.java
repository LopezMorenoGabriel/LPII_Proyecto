package com.ciberpet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.ciberpet.models.CarritoItem;
import com.ciberpet.services.CompraService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CompraController {


    @Autowired
    private CompraService compraService;
    
    @PostMapping("/finalizar-compra")
    public String finalizarCompra(HttpSession session) {
        boolean exitoso = compraService.finalizarCompra(session);
        return exitoso ? "redirect:/carrito?success=true" : "redirect:/carrito";
    }

    
    @GetMapping("/carrito")
    public String mostrarCarrito(
            @RequestParam(required = false) String success,
            Model model,
            @SessionAttribute(required = false) List<CarritoItem> carrito,
            HttpSession session) {

        if (session.getAttribute("usuarioSesion") == null) {
            return "redirect:/login";
        }

        compraService.prepararVistaCarrito(session, model, success, carrito);
        return "inicio/carrito";
    }

    
    @PostMapping("/agregarCompra")
	public String agregarCompra(@RequestParam String idProducto, @RequestParam int cantidad, HttpSession session) {
	    boolean agregado = compraService.agregarAlCarrito(idProducto, cantidad, session);
	    return agregado ? "redirect:/carrito" : "redirect:/filtradoProductos";
	}

	@PostMapping("/eliminarItem")
	public String eliminarItemDelCarrito(@RequestParam String idProducto, HttpSession session) {
	    compraService.eliminarItem(idProducto, session);
	    return "redirect:/carrito";
	}

	@PostMapping("/reducirCantidad")
	public String reducirCantidad(@RequestParam String idProducto, HttpSession session) {
	    compraService.reducirCantidad(idProducto, session);
	    return "redirect:/carrito";
	}

    
}
