package com.ciberpet.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ciberpet.dtos.DetalleCompraId;
import com.ciberpet.models.CarritoItem;
import com.ciberpet.models.Compra;
import com.ciberpet.models.DetCompra;
import com.ciberpet.models.Producto;
import com.ciberpet.models.Usuario;
import com.ciberpet.repositories.ICompraRepository;
import com.ciberpet.repositories.IDetCompraRespository;
import com.ciberpet.repositories.IProductoRepository;


@SuppressWarnings("unchecked")
@Service
public class CompraService {

    @Autowired
    private ICompraRepository compraRepository;

    @Autowired
    private IDetCompraRespository detalleCompraRepository;

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private ProductoService productoService;
    
    public List<Compra> listarComprasPorCliente(int idUsuario) {
        return compraRepository.findByUsuarioIdUser(idUsuario);
    }
    

    public void procesarCompra(List<CarritoItem> carrito, Usuario usuario) {
        Compra compra = new Compra();
        compra.setFechaBoleta(LocalDate.now());
        compra.setUsuario(usuario);
        compraRepository.save(compra);

        for (CarritoItem item : carrito) {
            DetalleCompraId id = new DetalleCompraId();
            id.setIdBoleta(compra.getIdBoleta());
            id.setIdProduct(item.getProducto().getIdProducto());

            DetCompra detalle = new DetCompra();
            detalle.setId(id);
            detalle.setCompra(compra);
            detalle.setProducto(item.getProducto());
            detalle.setCantidad(item.getCantidad());
            detalle.setTotal(item.getSubtotal());

            Producto producto = productoRepository.findById(item.getProducto().getIdProducto())
                                                  .orElseThrow();
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);

            detalleCompraRepository.save(detalle);
        }
    }

    
    public boolean agregarAlCarrito(String idProducto, int cantidad, HttpSession session) {
        Producto producto = productoService.getOne(idProducto);
        if (producto == null || cantidad < 1) {
            return false;
        }

        List<CarritoItem> carrito = (List<CarritoItem>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute("carrito", carrito);
        }

        Optional<CarritoItem> existente = carrito.stream()
                .filter(item -> item.getProducto().getIdProducto().equals(idProducto))
                .findFirst();

        if (existente.isPresent()) {
            existente.get().setCantidad(existente.get().getCantidad() + cantidad);
        } else {
            carrito.add(new CarritoItem(producto, cantidad));
        }

        return true;
    }

    
    public void eliminarItem(String idProducto, HttpSession session) {
        List<CarritoItem> carrito = (List<CarritoItem>) session.getAttribute("carrito");
        if (carrito != null) {
            carrito.removeIf(item -> item.getProducto().getIdProducto().equals(idProducto));
        }
    }

    
    public void reducirCantidad(String idProducto, HttpSession session) {
        List<CarritoItem> carrito = (List<CarritoItem>) session.getAttribute("carrito");
        if (carrito != null) {
            for (CarritoItem item : carrito) {
                if (item.getProducto().getIdProducto().equals(idProducto)) {
                    if (item.getCantidad() > 1) {
                        item.setCantidad(item.getCantidad() - 1);
                    } else {
                        carrito.remove(item);
                    }
                    break;
                }
            }
        }
    }
    
    public boolean finalizarCompra(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioSesion");
        if (usuario == null) return false;

        List<CarritoItem> carrito = (List<CarritoItem>) session.getAttribute("carrito");
        if (carrito == null || carrito.isEmpty()) return false;

        procesarCompra(carrito, usuario);
        carrito.clear();
        session.setAttribute("mensajeCompra", "¡Compra realizada con éxito!");

        return true;
    }
    
    public void prepararVistaCarrito(HttpSession session, Model model, String success, List<CarritoItem> carrito) {
        if ("true".equals(success)) {
            String mensaje = (String) session.getAttribute("mensajeCompra");
            if (mensaje != null) {
                model.addAttribute("mensajeCompra", mensaje);
                session.removeAttribute("mensajeCompra");
            }
        }

        if (carrito != null && !carrito.isEmpty()) {
            double total = carrito.stream().mapToDouble(CarritoItem::getSubtotal).sum();
            model.addAttribute("total", total);
        }

        model.addAttribute("carrito", carrito);
    }
    
}
