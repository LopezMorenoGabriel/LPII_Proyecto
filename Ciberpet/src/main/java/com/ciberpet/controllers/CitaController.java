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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ciberpet.dtos.CitaFilter;
import com.ciberpet.dtos.ResultadoResponse;
import com.ciberpet.models.Cita;
import com.ciberpet.models.Servicio;
import com.ciberpet.models.Usuario;
import com.ciberpet.services.CitaService;
import com.ciberpet.services.ClienteService;
import com.ciberpet.services.EstadoCitaService;
import com.ciberpet.services.ServicioService;
import com.ciberpet.utils.Alert;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private EstadoCitaService estadoCitaService;

    

    
    @GetMapping("/agendar-cita")
    public String agendarCita(Model model, HttpSession session, RedirectAttributes flash) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioSesion");

        if (usuario == null) {
            flash.addFlashAttribute("alert", Alert.sweetAlertError("Debes iniciar sesión para agendar una cita."));
            return "redirect:/login";
        }

        Cita cita = new Cita();
        cita.setUsuario(usuario);

        List<Servicio> servicios = servicioService.getActivos();

        // DEBUG: imprime en consola
        System.out.println("Servicios disponibles:");
        for (Servicio s : servicios) {
            System.out.println("ID: " + s.getIdServicio() + ", Nombre: " + s.getNombre() + ", Precio: " + s.getPrecio());
        }

        model.addAttribute("cita", cita);
        model.addAttribute("servicios", servicios);

        return "inicio/citas";
    }

    
    @PostMapping("/registrar-mi-cita")
    public String registrarMiCita(@Valid @ModelAttribute Cita cita,
                                  BindingResult bindingResult,
                                  Model model,
                                  HttpSession session,
                                  RedirectAttributes flash) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioSesion");
        if (usuario == null) {
            flash.addFlashAttribute("alert", Alert.sweetAlertError("Debes iniciar sesión para agendar una cita."));
            return "redirect:/login";
        }

        cita.setUsuario(usuario);  // Aseguramos que el usuario se asigne

        if (bindingResult.hasErrors()) {
            model.addAttribute("servicios", servicioService.getActivos());
            model.addAttribute("alert", Alert.sweetAlertInfo("Por favor, revisa los errores del formulario"));
            return "inicio/citas";
        }

        ResultadoResponse response = citaService.create(cita);

        if (!response.success) {
            model.addAttribute("servicios", servicioService.getActivos());
            model.addAttribute("alert", Alert.sweetAlertError(response.mensaje));
            return "inicio/citas";
        }

        flash.addFlashAttribute("toast", Alert.sweetToast(response.mensaje, "success", 5000));
        return "redirect:/";
    }
    
    
    
    @GetMapping("/admin/citas")
    public String filtrado(@ModelAttribute CitaFilter filtro, Model model) {
        List<Cita> lstCita = citaService.search(filtro);
        model.addAttribute("servicios", servicioService.getAll());
        model.addAttribute("estadoCitas", estadoCitaService.getAll());
        model.addAttribute("filtro", filtro);
        model.addAttribute("lstCita", lstCita);
        return "admin/citas/listadoCita";
    }

    @GetMapping("/admin/citas/crear")
    public String crear(Model model) {
        model.addAttribute("cita", new Cita());
        model.addAttribute("servicios", servicioService.getActivos());
        model.addAttribute("clientes", clienteService.getActivos());
        return "admin/citas/crearCita";
    }

    @PostMapping("/admin/citas/registrar")
    public String registrarCita(@Valid @ModelAttribute Cita cita,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes flash) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("clientes", clienteService.getActivos());
            model.addAttribute("servicios", servicioService.getActivos());
            model.addAttribute("alert", Alert.sweetAlertInfo("Por favor, revisa los errores del formulario"));
            return "admin/citas/crearCita";
        }

        ResultadoResponse response = citaService.create(cita);

        if (!response.success) {
            model.addAttribute("clientes", clienteService.getActivos());
            model.addAttribute("servicios", servicioService.getActivos());
            model.addAttribute("alert", Alert.sweetAlertError(response.mensaje));
            return "admin/citas/crearCita";
        }

        flash.addFlashAttribute("toast", Alert.sweetToast(response.mensaje, "success", 5000));
        return "redirect:/admin/citas";
    }

	/*
	 * @GetMapping("/admin/citas/editar/{id}") public String editar(@PathVariable
	 * Integer id, Model model) { Cita cita = citaService.getOne(id);
	 * model.addAttribute("clientes", clienteService.getAll());
	 * model.addAttribute("servicios", servicioService.getAll());
	 * model.addAttribute("cita", cita); return "admin/citas/editarCita"; }
	 * 
	 * @PostMapping("/admin/citas/guardar") public String
	 * guardar(@Valid @ModelAttribute Cita cita, BindingResult bindingResult, Model
	 * model, RedirectAttributes flash) {
	 * 
	 * if (bindingResult.hasErrors()) { model.addAttribute("clientes",
	 * clienteService.getAll()); model.addAttribute("servicios",
	 * servicioService.getAll()); model.addAttribute("alert",
	 * Alert.sweetAlertInfo("Revisa los errores del formulario")); return
	 * "admin/citas/editarCita"; }
	 * 
	 * ResultadoResponse response = citaService.update(cita);
	 * 
	 * if (!response.success) { model.addAttribute("clientes",
	 * clienteService.getAll()); model.addAttribute("servicios",
	 * servicioService.getAll()); model.addAttribute("alert",
	 * Alert.sweetAlertError(response.mensaje)); return "admin/citas/editarCita"; }
	 * 
	 * flash.addFlashAttribute("toast", Alert.sweetToast(response.mensaje,
	 * "success", 5000)); return "redirect:/admin/citas"; }
	 */
    
    @GetMapping("/admin/citas/editar/{id}")
    public String editarEstado(@PathVariable Integer id, Model model) {
        Cita cita = citaService.getOne(id);
        model.addAttribute("cita", cita);
        model.addAttribute("estadoCitas", citaService.getEstados()); 
        return "admin/citas/editarCita";
    }

    @PostMapping("/admin/citas/guardar-estado")
    public String guardarEstado(@ModelAttribute Cita cita, RedirectAttributes flash) {
        ResultadoResponse response = citaService.actualizarEstado(cita.getIdCita(), cita.getEstadoCita());

        if (!response.success) {
            flash.addFlashAttribute("alert", Alert.sweetAlertError(response.mensaje));
            return "redirect:/admin/citas/editar/" + cita.getIdCita();
        }

        flash.addFlashAttribute("toast", Alert.sweetToast(response.mensaje, "success", 5000));
        return "redirect:/admin/citas";
    }

}
