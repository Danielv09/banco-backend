package com.prueba.banco.controller;

import com.prueba.banco.dto.TransactionRequest;
import com.prueba.banco.dto.TransactionResponse;
import com.prueba.banco.entity.enums.TipoTransaccion;
import com.prueba.banco.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionControllerTest {

    @Test
    void shouldReturn201OnCreateTransaction() {
        TransactionService service = mock(TransactionService.class);
        TransactionController controller = new TransactionController(service);

        TransactionRequest req = new TransactionRequest();
        req.setTipo(TipoTransaccion.CONSIGNACION);
        req.setMonto(BigDecimal.valueOf(100));
        req.setProductoDestinoId(1L);

        TransactionResponse resp = new TransactionResponse();
        resp.setId(10L);
        resp.setTipo(TipoTransaccion.CONSIGNACION);
        resp.setMonto(BigDecimal.valueOf(100));
        resp.setProductoDestinoId(1L);
        resp.setFecha(LocalDateTime.now());

        when(service.crearTransaccion(req)).thenReturn(resp);

        ResponseEntity<TransactionResponse> response = controller.crear(req);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(10L, response.getBody().getId());
        assertEquals(TipoTransaccion.CONSIGNACION, response.getBody().getTipo());
    }
}