package com.ciberpet.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ciberpet.dtos.ItemCarritoDTO;
import com.ciberpet.dtos.ResultadoResponse;
import com.ciberpet.models.DetalleVenta;
import com.ciberpet.models.Producto;
import com.ciberpet.models.Usuario;
import com.ciberpet.models.Venta;
import com.ciberpet.repositories.IProductoRepository;
import com.ciberpet.repositories.IVentaRepository;

@Service
public class VentaService {

    @Autowired
    private IVentaRepository _ventaRepository;

    @Autowired
    private IProductoRepository _productoRepository;

    public List<Venta> listarVentas() {
        return _ventaRepository.findAllByOrderByIdVentaDesc();
    }

    public Venta getVentaById(int id) {
        return _ventaRepository.findById(id).orElseThrow();
    }

    public Venta findById(Integer id) {
        return _ventaRepository.findById(id).orElse(null);
    }

    @Transactional
    public ResultadoResponse registrarVenta(List<ItemCarritoDTO> carrito, Usuario usuario) {
        try {
            if (carrito == null || carrito.isEmpty()) {
                return new ResultadoResponse(false, "El carrito está vacío.");
            }

            Venta venta = new Venta();
            venta.setUsuario(usuario);
            venta.setIdEstado(true);
            venta.setDetalles(new ArrayList<>());

            BigDecimal totalVenta = BigDecimal.ZERO;

            for (ItemCarritoDTO item : carrito) {
                Producto producto = _productoRepository.findById(item.getIdProducto())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + item.getIdProducto()));

                if (producto.getStock() < item.getCantidad()) {
                    throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombreProducto());
                }

                DetalleVenta detalle = new DetalleVenta();
                detalle.setVenta(venta);
                detalle.setProducto(producto);
                detalle.setCantidad(item.getCantidad());
                
                detalle.setPrecioUnitario(producto.getPrecio());

                BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad()));
                detalle.setSubtotal(subtotal);

                venta.getDetalles().add(detalle);

                totalVenta = totalVenta.add(subtotal);

                producto.setStock(producto.getStock() - item.getCantidad());
                _productoRepository.save(producto);
            }

            venta.setTotal(totalVenta);

            _ventaRepository.save(venta);

            return new ResultadoResponse(true, "La venta se registró correctamente.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResultadoResponse(false, "Error al registrar venta: " + ex.getMessage());
        }
    }
}
