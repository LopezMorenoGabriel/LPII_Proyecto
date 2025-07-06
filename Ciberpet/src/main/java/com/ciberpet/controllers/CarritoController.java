package com.ciberpet.controllers;

import com.ciberpet.dtos.ItemCarritoDTO;
import com.ciberpet.models.Producto;
import com.ciberpet.services.ProductoService;
import com.ciberpet.utils.Alert;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String mostrarCarrito(HttpSession session, Model model) {
        List<ItemCarritoDTO> carrito = obtenerCarrito(session);
        BigDecimal total = carrito.stream()
                .map(ItemCarritoDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);
        return "inicio/carrito";
    }

    @PostMapping("/agregar")
    public String agregarAlCarrito(
            @RequestParam("idProducto") String idProducto,
            @RequestParam("cantidad") int cantidad,
            HttpSession session,
            RedirectAttributes flash) {

        Producto producto = productoService.getOne(idProducto);
        if (producto == null) {
            flash.addFlashAttribute("alert", Alert.sweetAlertError("Producto no encontrado."));
            return "redirect:/productos";
        }

        List<ItemCarritoDTO> carrito = (List<ItemCarritoDTO>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }

        Optional<ItemCarritoDTO> existente = carrito.stream()
            .filter(item -> item.getIdProducto().equals(idProducto))
            .findFirst();

        int stockDisponible = producto.getStock();

        if (existente.isPresent()) {
            ItemCarritoDTO item = existente.get();
            int cantidadActual = item.getCantidad();
            int nuevaCantidadSugerida = cantidadActual + cantidad;

            // No permitir superar el stock
            if (nuevaCantidadSugerida > stockDisponible) {
                item.setCantidad(stockDisponible); // Ajustar al máximo permitido
            } else {
                item.setCantidad(nuevaCantidadSugerida);
            }

            item.setSubtotal(producto.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad())));

        } else {
            int cantidadFinal = Math.min(cantidad, stockDisponible);

            if (cantidadFinal <= 0) {
                flash.addFlashAttribute("alert", Alert.sweetAlertError("Stock insuficiente para este producto."));
                return "redirect:/productos";
            }

            ItemCarritoDTO nuevoItem = new ItemCarritoDTO(
                producto.getIdProducto(),
                producto.getNombreProducto(),
                producto.getPrecio(),
                cantidadFinal,
                producto.getPrecio().multiply(BigDecimal.valueOf(cantidadFinal))
            );

            carrito.add(nuevoItem);
        }

        session.setAttribute("carrito", carrito);

        flash.addFlashAttribute("toast", Alert.sweetToast("Producto agregado al carrito", "success", 3000));
        return "redirect:/carrito";
    }

    
    @GetMapping("/verCarrito")
    public String verCarrito(HttpSession session, Model model) {
        List<ItemCarritoDTO> carrito = (List<ItemCarritoDTO>) session.getAttribute("carrito");
        BigDecimal total = BigDecimal.ZERO;

        if (carrito != null) {
            for (ItemCarritoDTO item : carrito) {
                total = total.add(item.getSubtotal());
            }
        }

        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);

        return "carrito"; 
    }


    @PostMapping("/eliminar")
    public String eliminarDelCarrito(@RequestParam String idProducto, HttpSession session) {
        List<ItemCarritoDTO> carrito = obtenerCarrito(session);
        carrito.removeIf(i -> i.getIdProducto().equals(idProducto));
        session.setAttribute("carrito", carrito);
        return "redirect:/carrito";
    }

    @PostMapping("/vaciar")
    public String vaciarCarrito(HttpSession session) {
        session.setAttribute("carrito", new ArrayList<>());
        return "redirect:/carrito";
    }

    private List<ItemCarritoDTO> obtenerCarrito(HttpSession session) {
        List<ItemCarritoDTO> carrito = (List<ItemCarritoDTO>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute("carrito", carrito);
        }
        return carrito;
    }
}
