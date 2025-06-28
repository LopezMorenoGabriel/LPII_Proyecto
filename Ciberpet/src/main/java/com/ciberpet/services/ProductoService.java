package com.ciberpet.services;

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
		return _productoRepository.findAllWithFilters(filtro.getIdCategoria());
	}

	public Producto getOne(String id) {
		return _productoRepository.findById(id).orElseThrow();
	}
	
	public List<Producto> findByCategoriaDescripcion(String descripcion) {
	    return _productoRepository.findByCategoriaDescripcion(descripcion);
	}
	
	public ResultadoResponse create(Producto producto) {
		try {
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
			Producto registrado = _productoRepository.save(producto);

			String mensaje = String.format("Producto con código %s actualizado", registrado.getIdProducto());
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

}
