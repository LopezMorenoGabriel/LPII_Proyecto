package com.ciberpet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ciberpet.dtos.AutenticacionFilter;
import com.ciberpet.dtos.RegistroUsuarioDTO;
import com.ciberpet.dtos.ResultadoResponse;
import com.ciberpet.models.Usuario;
import com.ciberpet.services.AutenticacionService;
import com.ciberpet.utils.Alert;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AccesoController {

	@Autowired
	private AutenticacionService autenticacionService;

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("filter", new AutenticacionFilter());
		return "acceso/login";
	}

	@PostMapping("/iniciar-sesion")
	public String iniciarSesion(@ModelAttribute AutenticacionFilter filter, 
	                            HttpSession session, 
	                            RedirectAttributes flash) {
	    Usuario usuarioValidado = autenticacionService.autenticar(filter);

	    if (usuarioValidado == null) {
	        flash.addFlashAttribute("alert", Alert.sweetAlertError("Usuario y/o clave incorrecta"));
	        return "redirect:/login";
	    }

	    if (usuarioValidado.getIdEstado() == null || !usuarioValidado.getIdEstado()) {
	        flash.addFlashAttribute("alert", Alert.sweetAlertError("Tu cuenta está desactivada o restringida. Por favor, contacta al soporte."));
	        return "redirect:/login";
	    }

	    session.setAttribute("idUsuario", usuarioValidado.getIdUsuario());
	    session.setAttribute("nombreCompleto", usuarioValidado.getNombreCompleto());
	    session.setAttribute("cuenta", usuarioValidado.getCorreo());
	    session.setAttribute("rol", usuarioValidado.getTipo().getDescripcion());
	    session.setAttribute("usuarioSesion", usuarioValidado);

	    flash.addFlashAttribute("toast", Alert.sweetToast("Bienvenido, " + usuarioValidado.getNombre() + "!", "success", 4000));

	    if ("Administrador".equalsIgnoreCase(usuarioValidado.getTipo().getDescripcion())) {
	        return "redirect:/admin/dashboard";
	    } else {
	        return "redirect:/";
	    }
	}

	@PostMapping("/cerrar-sesion")
	public String cerrarSesion(HttpSession session) {
	    session.invalidate();
	    return "redirect:/";
	}
	
	@GetMapping("/registrar-usuario")
	public String mostrarRegistro(Model model) {
	    if (!model.containsAttribute("usuario")) {
	        model.addAttribute("usuario", new RegistroUsuarioDTO());
	    }
	    return "acceso/registro";
	}

	@PostMapping("/registrar-usuario")
	public String registrarUsuario(@Valid @ModelAttribute("usuario") RegistroUsuarioDTO usuarioDto, 
	                               BindingResult bindingResult,
	                               Model model, 
	                               RedirectAttributes flash) {

	    if (bindingResult.hasErrors()) {
	        model.addAttribute("alert", Alert.sweetAlertInfo("Por favor, revisa los errores en el formulario."));
	        return "acceso/registro";
	    }
	    
	    ResultadoResponse response = autenticacionService.createFromDto(usuarioDto);

	    if (!response.success) {
	        model.addAttribute("alert", Alert.sweetAlertError(response.mensaje));
	        return "acceso/registro";
	    }

	    flash.addFlashAttribute("toast", Alert.sweetToast("¡Cuenta creada con éxito! Por favor, inicia sesión.", "success", 5000));
	    return "redirect:/login";
	}

}
