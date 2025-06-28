package com.ciberpet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ciberpet.dtos.AutentacionFilter;
import com.ciberpet.dtos.ResultadoResponse;
import com.ciberpet.models.Tipo;
import com.ciberpet.models.Usuario;
import com.ciberpet.services.AutenticacionService;
import com.ciberpet.utils.Alert;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AccesoController {

	@Autowired
	private AutenticacionService autenticacionService;

	@GetMapping({ "/", "/login" })
	public String login(Model model) {
		model.addAttribute("filter", new AutentacionFilter());
		return "acceso/login";
	}

	@PostMapping("/iniciar-sesion")
	public String iniciarSesion(@ModelAttribute AutentacionFilter filter, HttpSession session, Model model,
	                            RedirectAttributes flash) {
	    Usuario usuarioValidado = autenticacionService.autenticar(filter);

	    if (usuarioValidado == null) {
	        model.addAttribute("filter", new AutentacionFilter());
	        model.addAttribute("alert", Alert.sweetAlertError("Usuario y/o clave incorrecta"));
	        return "acceso/login";
	    }

	    session.setAttribute("idUsuario", usuarioValidado.getIdUsuario());
	    session.setAttribute("nombreCompleto", usuarioValidado.getNombre() + " " + usuarioValidado.getApellidos());
	    session.setAttribute("cuenta", usuarioValidado.getCorreo());
	    session.setAttribute("rol", usuarioValidado.getTipo().getDescripcion());
	    session.setAttribute("usuarioSesion", usuarioValidado);


	    flash.addFlashAttribute("alert", Alert.sweetAlertSuccess("Bienvenido, " + usuarioValidado.getNombre() + "!"));

	    if ("Administrador".equalsIgnoreCase(usuarioValidado.getTipo().getDescripcion())) {
	        return "redirect:/dashboard";
	    } else {
	        return "redirect:/productos";
	    }
	}


	@GetMapping("/home")
	public String home(HttpSession session, Model model) {
		if (session.getAttribute("cuenta") == null)
			return "redirect:/login";
		return "home";
	}

	@GetMapping("/cerrar-sesion")
	public String cerrarSesion(HttpSession session) {
		session.invalidate();
		return "redirect:/inicio";
	}
	
	@GetMapping("/registrar-usuario")
	public String mostrarRegistro(Model model) {
	    model.addAttribute("usuario", new Usuario());
	    return "acceso/registro";
	}
	
	@PostMapping("/registrar-usuario")
	public String registrarUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult bindingResult,
	                               Model model, RedirectAttributes flash) {

	    if (bindingResult.hasErrors()) {
	        model.addAttribute("alert", Alert.sweetAlertInfo("Falta completar información"));
	        model.addAttribute("usuario", usuario);
	        return "acceso/registro";
	    }

	    try {
	        Tipo tipoCliente = new Tipo();
	        tipoCliente.setIdTipo(2);
	        usuario.setTipo(tipoCliente);

	        ResultadoResponse response = autenticacionService.create(usuario);

	        if (!response.success) {
	            model.addAttribute("alert", Alert.sweetAlertError(response.mensaje));
	            model.addAttribute("usuario", usuario);
	            return "acceso/registro";
	        }

	        flash.addFlashAttribute("toast", Alert.sweetToast("Usuario registrado correctamente.", "success", 5000));
	        return "redirect:/login";

	    } catch (Exception e) {
	        model.addAttribute("alert", Alert.sweetAlertError("Error al registrar: " + e.getMessage()));
	        model.addAttribute("usuario", usuario);
	        return "acceso/registro";
	    }
	}


}
