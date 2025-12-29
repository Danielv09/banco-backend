package com.prueba.banco.controller;

import com.prueba.banco.dto.TransactionRequest;
import com.prueba.banco.dto.TransactionResponse;
import com.prueba.banco.entity.enums.TipoTransaccion;
import com.prueba.banco.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionControllerTest {

    @Test
    void shouldReturn201OnCreateTransaction() {
        TransactionService service = mock(TransactionService.class);
        TransactionController controller = new TransactionController(service);

        TransactionRequest req = new TransactionRequest();
        req.setTipo(TipoTransaccion.DEPOSITO);
        req.setMonto(BigDecimal.valueOf(500));
        req.setProductoDestinoId(1L);

        TransactionResponse resp = new TransactionResponse();
        resp.setId(10L);
        resp.setTipo(TipoTransaccion.DEPOSITO);
        resp.setMonto(BigDecimal.valueOf(500));
        resp.setFecha(LocalDateTime.now());

        when(service.crearTransaccion(req)).thenReturn(resp);

        ResponseEntity<TransactionResponse> response = controller.crear(req);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(10L, response.getBody().getId());
        assertEquals(TipoTransaccion.DEPOSITO, response.getBody().getTipo());
    }

    @Test
    void shouldReturnTransactionById() {
        TransactionService service = mock(TransactionService.class);
        TransactionController controller = new TransactionController(service);

        TransactionResponse resp = new TransactionResponse();
        resp.setId(5L);
        resp.setTipo(TipoTransaccion.RETIRO);
        resp.setMonto(BigDecimal.valueOf(200));

        when(service.obtenerTransaccionPorId(5L)).thenReturn(resp);

        ResponseEntity<TransactionResponse> response = controller.obtener(5L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(5L, response.getBody().getId());
        assertEquals(TipoTransaccion.RETIRO, response.getBody().getTipo());
    }

    @Test
    void shouldListTransactions() {
        TransactionService service = mock(TransactionService.class);
        TransactionController controller = new TransactionController(service);

        TransactionResponse t1 = new TransactionResponse();
        t1.setId(1L);
        TransactionResponse t2 = new TransactionResponse();
        t2.setId(2L);

        when(service.listarTransacciones()).thenReturn(Arrays.asList(t1, t2));

        ResponseEntity<List<TransactionResponse>> response = controller.listar();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }
}