package com.ciberpet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ciberpet.dtos.ProductoFilter;
import com.ciberpet.dtos.ResultadoResponse;
import com.ciberpet.models.Producto;
import com.ciberpet.services.CategoriaService;
import com.ciberpet.services.ProductoService;
import com.ciberpet.utils.Alert;

import jakarta.validation.Valid;

@Controller
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping("/mostrarProducto")
    public String mostrarProducto(@RequestParam("codigo") String id, Model model) {
        Producto p = productoService.getOne(id);
        model.addAttribute("p", p);
        return "inicio/mostrarProducto";
    }
	
	@GetMapping("/filtradoProductos")
	public String filtrado(@ModelAttribute ProductoFilter filtro, Model model) {
		
		List<Producto> lstProducto = productoService.search(filtro);
		
		model.addAttribute("categorias", categoriaService.getAll());
		model.addAttribute("filtro", filtro);
		model.addAttribute("lstProducto", lstProducto);
		
		return "dashboard/productos/filtradoProductos";
	}
	
	@GetMapping("/crearProductos")
	public String crearProductos(Model model) {
		Producto producto = new Producto();
		producto.setEstado(true);
		model.addAttribute("categorias", categoriaService.getAll());
		model.addAttribute("producto", producto);
		
		return "dashboard/productos/crearProductos";
	}
	
	@PostMapping("/registrarProducto")
	public String registrarProducto(@Valid @ModelAttribute Producto producto, BindingResult bindingResult, Model model,
			RedirectAttributes flash) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("categorias", categoriaService.getAll());
			model.addAttribute("alert", Alert.sweetAlertInfo("Falta completar información"));
			return "dashboard/productos/crearProductos";
		}

		ResultadoResponse response = productoService.create(producto);

		if (!response.success) {
			model.addAttribute("categorias", categoriaService.getAll());
			model.addAttribute("alert", Alert.sweetAlertError(response.mensaje));
			return "dashboard/productos/crearProductos";
		}

		String toast = Alert.sweetToast(response.mensaje, "success", 5000);
		flash.addFlashAttribute("toast", toast);
		return "redirect:/filtradoProductos";
	}
	
	@GetMapping("/edicionProducto/{id}")
	public String edicion(@PathVariable String id, Model model) {
		
		model.addAttribute("categorias", categoriaService.getAll());
		Producto producto = productoService.getOne(id);
		model.addAttribute("producto", producto);
		
		return "dashboard/productos/edicionProducto";
	}

	@PostMapping("/guardarProducto")
	public String guardar(@Valid @ModelAttribute Producto producto, BindingResult bindingResult, Model model,
			RedirectAttributes flash) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("categorias", categoriaService.getAll());
			model.addAttribute("alert", Alert.sweetAlertInfo("Falta completar información"));
			return "dashboard/productos/edicionProducto";
		}

		ResultadoResponse response = productoService.update(producto);

		if (!response.success) {
			model.addAttribute("categorias", categoriaService.getAll());
			model.addAttribute("alert", Alert.sweetAlertError(response.mensaje));
			return "dashboard/productos/edicionProducto";
		}

		String toast = Alert.sweetToast(response.mensaje, "success", 5000);
		flash.addFlashAttribute("toast", toast);
		return "redirect:/filtradoProductos";
	}
	
	@PostMapping("/cambiar-estado/{id}")
	public String cambiarEstado(@PathVariable String id, RedirectAttributes flash) {

		ResultadoResponse response = productoService.cambiarEstado(id);
		
		String toast = Alert.sweetToast(response.mensaje, "success", 5000);
		flash.addFlashAttribute("toast", toast);
		return "redirect:/filtradoProductos";
	}

	
}
