package com.ciberpet.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ciberpet.dtos.ProductoFilter;
import com.ciberpet.dtos.ResultadoResponse;
import com.ciberpet.models.Producto;
import com.ciberpet.repositories.IProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private IProductoRepository _productoRepository;

    public List<Producto> lstProductos() {
        return _productoRepository.findAllByOrderByIdProductoDesc();
    }

    public List<Producto> search(ProductoFilter filtro) {
        return _productoRepository.findAllWithFilters(filtro.getIdCategoria(), filtro.getIdEstado());
    }

    public Producto getOne(String id) {
        return _productoRepository.findById(id).orElseThrow();
    }

    public List<Producto> findByCategoriaDescripcion(String descripcion) {
        return _productoRepository.findByCategoriaDescripcion(descripcion);
    }

    public ResultadoResponse create(Producto producto) {
        try {
            if (_productoRepository.existsById(producto.getIdProducto())) {
                return new ResultadoResponse(false, "El código de producto " + producto.getIdProducto() + " ya existe.");
            }

            Producto registrado = _productoRepository.save(producto);
            String mensaje = String.format("Producto con código %s registrado", registrado.getIdProducto());
            return new ResultadoResponse(true, mensaje);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResultadoResponse(false, "Error al registrar: " + ex.getMessage());
        }
    }

    public ResultadoResponse update(Producto producto) {
        try {
            if (!_productoRepository.existsById(producto.getIdProducto())) {
                return new ResultadoResponse(false, "No se puede actualizar. El producto con código " + producto.getIdProducto() + " no existe.");
            }

            Producto actualizado = _productoRepository.save(producto);
            String mensaje = String.format("Producto con código %s actualizado", actualizado.getIdProducto());
            return new ResultadoResponse(true, mensaje);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResultadoResponse(false, "Error al actualizar: " + ex.getMessage());
        }
    }

    public ResultadoResponse delete(String idProducto) {
        try {
            if (_productoRepository.existsById(idProducto)) {
                _productoRepository.deleteById(idProducto);
                String mensaje = String.format("Producto con código %s eliminado correctamente", idProducto);
                return new ResultadoResponse(true, mensaje);
            } else {
                return new ResultadoResponse(false, "El producto no existe en la base de datos.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResultadoResponse(false, "Error al eliminar: " + ex.getMessage());
        }
    }

    public ResultadoResponse cambiarEstado(String id) {
        Producto producto = this.getOne(id);
        String accion = producto.getIdEstado() ? "desactivado" : "activado";

        producto.setIdEstado(!producto.getIdEstado());

        try {
            Producto registrado = _productoRepository.save(producto);
            String mensaje = String.format("Producto con código %s %s", registrado.getIdProducto(), accion);
            return new ResultadoResponse(true, mensaje);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResultadoResponse(false, "Error al cambiar de estado: " + ex.getMessage());
        }
    }

    public List<Producto> getActivos() {
        return _productoRepository.findAllByIdEstado(true);
    }

    // ✅ Método para dashboard corregido con LocalDate
    public long countProductosDelMes() {
        LocalDate inicio = LocalDate.now().withDayOfMonth(1);
        LocalDate fin = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        return _productoRepository.countByFechaRegistroBetween(inicio, fin);
    }
}
