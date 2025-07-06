package com.ciberpet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ciberpet.dtos.ItemCarritoDTO;
import com.ciberpet.dtos.ResultadoResponse;
import com.ciberpet.models.Usuario;
import com.ciberpet.models.Venta;
import com.ciberpet.services.VentaService;
import com.ciberpet.utils.Alert;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.xhtmlrenderer.pdf.ITextRenderer;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.servlet.http.HttpServletResponse;

import java.io.OutputStream;

import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.math.RoundingMode;


@Controller
public class VentaController {

@Autowired
private SpringTemplateEngine templateEngine;

    @Autowired
    private VentaService ventaService;

    @GetMapping("/admin/ventas")
    public String listarVentas(Model model, HttpServletRequest request) {
        model.addAttribute("currentUri", request.getRequestURI());

        List<Venta> ventas = ventaService.listarVentas();
        model.addAttribute("lstVentas", ventas);

        return "admin/ventas/listadoVenta";
    }
    
    @GetMapping("/admin/ventas/detalle/{id}")
    public String detalleVenta(@PathVariable("id") Integer id, Model model, RedirectAttributes redirect) {
        Venta venta = ventaService.findById(id);
        if (venta == null) {
            redirect.addFlashAttribute("alert", Alert.sweetAlertError("No se encontró la venta"));
            return "redirect:/admin/ventas";
        }
        model.addAttribute("venta", venta);
        return "admin/ventas/detalleVenta";
    }
    
    @PostMapping("/procesarPedido")
    public String procesarPedido(HttpSession session, Model model, RedirectAttributes flash) {
        @SuppressWarnings("unchecked")
		List<ItemCarritoDTO> carrito = (List<ItemCarritoDTO>) session.getAttribute("carrito");
        Usuario usuario = (Usuario) session.getAttribute("usuarioSesion");

        if (usuario == null) {
            flash.addFlashAttribute("alert", Alert.sweetAlertError("Debes iniciar sesión para procesar el pedido."));
            return "redirect:/login";
        }

        if (carrito == null || carrito.isEmpty()) {
            flash.addFlashAttribute("alert", Alert.sweetAlertError("El carrito está vacío."));
            return "redirect:/carrito";
        }

        try {
            ResultadoResponse response = ventaService.registrarVenta(carrito, usuario);
            if (!response.success) {
                flash.addFlashAttribute("alert", Alert.sweetAlertError(response.mensaje));
                return "redirect:/carrito";
            }

            session.removeAttribute("carrito");

            model.addAttribute("nombre", usuario.getNombreCompleto());
            model.addAttribute("mensaje", "Gracias " + usuario.getNombreCompleto() + " por tu compra.");
            
            return "inicio/confirmacionPedido";

        } catch (Exception ex) {
            ex.printStackTrace();
            flash.addFlashAttribute("alert", Alert.sweetAlertError("Error al procesar el pedido: " + ex.getMessage()));
            return "redirect:/carrito";
        }
    }


    @GetMapping("/admin/ventas/factura/{id}")
    public void generarFacturaPdf(@PathVariable("id") Integer id, HttpServletResponse response) throws Exception {
        Venta venta = ventaService.findById(id);
        if (venta == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Venta no encontrada");
            return;
        }

        // Calcular subtotal e impuesto (18%)
        BigDecimal totalVenta = venta.getTotal();
        BigDecimal divisor = new BigDecimal("1.18");
        BigDecimal subtotal = totalVenta.divide(divisor, 2, RoundingMode.HALF_UP);
        BigDecimal impuesto = totalVenta.subtract(subtotal);

        // Formateo de la fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // Construir el HTML
        StringBuilder html = new StringBuilder();
        html.append("<html><head>");
        html.append("<style>")
            .append("body { font-family: Arial, sans-serif; font-size: 12px; }")
            .append(".header { text-align: center; margin-bottom: 20px; }")
            .append(".logo { font-size: 24px; font-weight: bold; color: #555; }")
            .append(".tabla { width: 100%; border-collapse: collapse; margin-top: 20px; }")
            .append(".tabla th, .tabla td { border: 1px solid #ccc; padding: 8px; text-align: left; }")
            .append(".tabla th { background-color: #f2f2f2; }")
            .append(".totales { width: 100%; margin-top: 10px; }")
            .append(".totales td { padding: 5px; }")
            .append(".label { text-align: right; font-weight: bold; }")
            .append(".valor { text-align: right; }")
            .append("</style>")
            .append("</head><body>");

        html.append("<div class='header'>")
            .append("<div class='logo'>🐾 CiberPet</div>")
            .append("<h2>Factura N° ").append(venta.getIdVenta()).append("</h2>")
            .append("<p>Fecha: ").append(venta.getFechaVenta().format(formatter)).append("</p>")
            .append("</div>");

        html.append("<p><strong>Cliente:</strong> ").append(venta.getUsuario().getNombreCompleto()).append("</p>")
            .append("<p><strong>Correo:</strong> ").append(venta.getUsuario().getCorreo()).append("</p>")
            .append("<p><strong>Teléfono:</strong> ").append(venta.getUsuario().getTelefono()).append("</p>");

        html.append("<table class='tabla'>")
            .append("<tr>")
            .append("<th>#</th>")
            .append("<th>Producto</th>")
            .append("<th>Cantidad</th>")
            .append("<th>Precio Unitario</th>")
            .append("<th>Subtotal</th>")
            .append("</tr>");

        int count = 1;
        for (var detalle : venta.getDetalles()) {
            html.append("<tr>")
                .append("<td>").append(count++).append("</td>")
                .append("<td>").append(detalle.getProducto().getNombreProducto()).append("</td>")
                .append("<td>").append(detalle.getCantidad()).append("</td>")
                .append("<td>").append("S/. ").append(detalle.getPrecioUnitario()).append("</td>")
                .append("<td>").append("S/. ").append(detalle.getSubtotal()).append("</td>")
                .append("</tr>");
        }

        html.append("</table>");

        html.append("<table class='totales'>")
            .append("<tr><td class='label'>Subtotal:</td><td class='valor'>S/. ").append(subtotal).append("</td></tr>")
            .append("<tr><td class='label'>Impuestos (18%):</td><td class='valor'>S/. ").append(impuesto).append("</td></tr>")
            .append("<tr><td class='label'>Total:</td><td class='valor' style='border-top:1px solid #333; font-size:14px;'>S/. ")
            .append(totalVenta).append("</td></tr>")
            .append("</table>");

        html.append("<p style='text-align:center; margin-top:30px;'>¡Gracias por su compra!</p>");

        html.append("</body></html>");

        // Generar el PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=ciberpet_factura_" + venta.getIdVenta() + ".pdf");

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html.toString());
        renderer.layout();

        try (OutputStream os = response.getOutputStream()) {
            renderer.createPDF(os);
            os.flush();
        }
    }
}
