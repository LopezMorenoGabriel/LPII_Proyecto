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

import com.ciberpet.dtos.CitaFilter;
import com.ciberpet.dtos.ResultadoResponse;
import com.ciberpet.models.Cita;
import com.ciberpet.models.Usuario;
import com.ciberpet.services.CitaService;
import com.ciberpet.services.ClienteService;
import com.ciberpet.services.ServicioService;
import com.ciberpet.utils.Alert;

import jakarta.validation.Valid;

@Controller
public class CitaController {
	@Autowired
	private ServicioService servicioService;

	@Autowired
	private CitaService citaService;
	
	@Autowired
	private ClienteService clienteService;

	@GetMapping("/nuevaCita/{idUsuario}")
	public String nuevaCitaDesdeCliente(@PathVariable int idUsuario, Model model) {
	    Cita cita = new Cita();
	    Usuario cliente = clienteService.getOne(idUsuario);
	    cita.setUsuario(cliente);

	    model.addAttribute("cita", cita);
	    model.addAttribute("servicios", servicioService.getAll());
	    return "dashboard/citas/nuevaCita";
	}

	@PostMapping("/citas/registrarCita")
	public String registrarCita(@Valid @ModelAttribute Cita cita, BindingResult bindingResult, Model model,
			RedirectAttributes flash) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("servicios", servicioService.getAll());
			model.addAttribute("alert", Alert.sweetAlertInfo("Falta completar información"));
			Usuario cliente = clienteService.getOne(cita.getUsuario().getIdUsuario());
			cita.setUsuario(cliente);
			model.addAttribute("cita", cita);
			return "dashboard/citas/nuevaCita";
		}

		ResultadoResponse response = citaService.create(cita);

		if (!response.success) {
			model.addAttribute("servicios", servicioService.getAll());
			model.addAttribute("alert", Alert.sweetAlertError(response.mensaje));
			return "dashboard/citas/nuevaCita";
		}

		String toast = Alert.sweetToast(response.mensaje, "success", 5000);
		flash.addFlashAttribute("toast", toast);
		return "redirect:/filtradoCitas";
	}

	@GetMapping("/filtradoCitas")
	public String filtradoCitas(@ModelAttribute CitaFilter filtro, Model model) {
		List<Cita> lstCita = citaService.search(filtro);

		model.addAttribute("servicios", servicioService.getAll());
		model.addAttribute("filtro", filtro);
		model.addAttribute("lstCita", lstCita);
		
		return "dashboard/citas/filtradoCitas";
	}

	@GetMapping("/edicionCita/{id}")
	public String edicionCita(@PathVariable int id, Model model) {
		
		model.addAttribute("servicios", servicioService.getAll());
		Cita cita = citaService.getOne(id);
		model.addAttribute("cita", cita);
		
		return "dashboard/citas/edicionCita";
	}

	@PostMapping("/guardarCita")
	public String guardarCita(@Valid @ModelAttribute Cita cita, BindingResult bindingResult, Model model,
			RedirectAttributes flash) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("servicios", servicioService.getAll());
			model.addAttribute("alert", Alert.sweetAlertInfo("Falta completar información"));
			return "dashboard/citas/edicionCita";
		}

		ResultadoResponse response = citaService.update(cita);
		
		if (!response.success) {
			model.addAttribute("servicios", servicioService.getAll());
			model.addAttribute("alert", Alert.sweetAlertError(response.mensaje));
			return "dashboard/citas/edicionCita";
		}
		String toast = Alert.sweetToast(response.mensaje, "success", 5000);
		flash.addFlashAttribute("toast", toast);

		return "redirect:/filtradoCitas";
	}
	
	@GetMapping("/eliminarCita/{id}")
	public String eliminarCita(@PathVariable int id, RedirectAttributes flash, Model model) {
		Cita cita = citaService.getOne(id);

	    if (cita == null) {
	        String toast = Alert.sweetToast("Cita no encontrado", "error", 5000);
	        model.addAttribute("toast", toast);
	        return "redirect:/filtradoCitas";
	    }

	    model.addAttribute("cita", cita);
	    return "dashboard/citas/eliminarCita"; 
	}
	
	@PostMapping("/eliminarCita")
	public String confirmarEliminarCita(@RequestParam("idCita") int id, RedirectAttributes flash) {
	    ResultadoResponse response = citaService.delete(id);

	    String toast = Alert.sweetToast(response.mensaje, "success", 5000);
		flash.addFlashAttribute("toast", toast);

	    return "redirect:/filtradoCitas";
	}

}
