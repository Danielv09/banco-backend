package com.prueba.banco.controller;

import com.prueba.banco.dto.ProductRequest;
import com.prueba.banco.dto.ProductResponse;
import com.prueba.banco.dto.EstadoRequest;
import com.prueba.banco.entity.enums.EstadoCuenta;
import com.prueba.banco.entity.enums.TipoCuenta;
import com.prueba.banco.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Test
    void shouldReturn201OnCreateProduct() {
        ProductService service = mock(ProductService.class);
        ProductController controller = new ProductController(service);

        ProductRequest req = new ProductRequest();
        req.setTipoCuenta(TipoCuenta.AHORROS);
        req.setSaldo(BigDecimal.valueOf(500));
        req.setClienteId(1L);

        ProductResponse resp = new ProductResponse();
        resp.setId(10L);
        resp.setNumeroCuenta("5312345678");
        resp.setTipoCuenta(TipoCuenta.AHORROS);
        resp.setEstado(EstadoCuenta.ACTIVA);
        resp.setSaldo(BigDecimal.valueOf(500));
        resp.setClienteId(1L);
        resp.setFechaCreacion(LocalDateTime.now());

        when(service.crearProducto(req)).thenReturn(resp);

        ResponseEntity<ProductResponse> response = controller.crearProducto(req);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(10L, response.getBody().getId());
    }

    @Test
    void shouldReturnProductById() {
        ProductService service = mock(ProductService.class);
        ProductController controller = new ProductController(service);

        ProductResponse resp = new ProductResponse();
        resp.setId(5L);
        resp.setNumeroCuenta("5312345678");

        when(service.obtenerProductoPorId(5L)).thenReturn(resp);

        ResponseEntity<ProductResponse> response = controller.obtenerProductoPorId(5L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(5L, response.getBody().getId());
    }

    @Test
    void shouldListProducts() {
        ProductService service = mock(ProductService.class);
        ProductController controller = new ProductController(service);

        ProductResponse p1 = new ProductResponse();
        p1.setId(1L);
        ProductResponse p2 = new ProductResponse();
        p2.setId(2L);

        when(service.listarProductos()).thenReturn(Arrays.asList(p1, p2));

        ResponseEntity<List<ProductResponse>> response = controller.listarProductos();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void shouldUpdateProduct() {
        ProductService service = mock(ProductService.class);
        ProductController controller = new ProductController(service);

        ProductRequest req = new ProductRequest();
        req.setSaldo(BigDecimal.valueOf(1000));

        ProductResponse resp = new ProductResponse();
        resp.setId(3L);
        resp.setSaldo(BigDecimal.valueOf(1000));

        when(service.actualizarProducto(3L, req)).thenReturn(resp);

        ResponseEntity<ProductResponse> response = controller.actualizarProducto(3L, req);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1000, response.getBody().getSaldo().intValue());
    }

    @Test
    void shouldUpdateEstado() {
        ProductService service = mock(ProductService.class);
        ProductController controller = new ProductController(service);

        EstadoRequest req = new EstadoRequest();
        req.setEstado(EstadoCuenta.INACTIVA);

        ProductResponse resp = new ProductResponse();
        resp.setId(4L);
        resp.setEstado(EstadoCuenta.INACTIVA);

        when(service.actualizarEstado(4L, EstadoCuenta.INACTIVA)).thenReturn(resp);

        ResponseEntity<ProductResponse> response = controller.actualizarEstado(4L, req);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(EstadoCuenta.INACTIVA, response.getBody().getEstado());
    }

    @Test
    void shouldDeleteProduct() {
        ProductService service = mock(ProductService.class);
        ProductController controller = new ProductController(service);

        doNothing().when(service).eliminarProducto(6L);

        ResponseEntity<String> response = controller.eliminarProducto(6L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("El producto ha sido eliminado exitosamente", response.getBody());
    }
}