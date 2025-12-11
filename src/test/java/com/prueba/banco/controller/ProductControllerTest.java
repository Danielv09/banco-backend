package com.prueba.banco.controller;

import com.prueba.banco.dto.ProductRequest;
import com.prueba.banco.dto.ProductResponse;
import com.prueba.banco.entity.enums.EstadoCuenta;
import com.prueba.banco.entity.enums.TipoCuenta;
import com.prueba.banco.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
        assertEquals(TipoCuenta.AHORROS, response.getBody().getTipoCuenta());
        assertEquals(EstadoCuenta.ACTIVA, response.getBody().getEstado());
        assertEquals(10, response.getBody().getNumeroCuenta().length());
        assertTrue(response.getBody().getNumeroCuenta().startsWith("53"));
    }
}