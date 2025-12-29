package com.prueba.banco.service;

import com.prueba.banco.dto.TransactionRequest;
import com.prueba.banco.dto.TransactionResponse;
import com.prueba.banco.entity.ProductEntity;
import com.prueba.banco.entity.TransactionEntity;
import com.prueba.banco.entity.enums.EstadoCuenta;
import com.prueba.banco.entity.enums.TipoTransaccion;
import com.prueba.banco.exception.BusinessException;
import com.prueba.banco.exception.NotFoundException;

import com.prueba.banco.repository.ProductRepository;
import com.prueba.banco.repository.TransactionRepository;
import com.prueba.banco.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionServiceImplTest {

    private TransactionRepository transactionRepository;
    private ProductRepository productRepository;
    private TransactionServiceImpl service;

    @BeforeEach
    void setup() {
        transactionRepository = mock(TransactionRepository.class);
        productRepository = mock(ProductRepository.class);
        service = new TransactionServiceImpl(transactionRepository, productRepository);
    }

    @Test
    void shouldCreateDepositTransaction() {
        ProductEntity destino = new ProductEntity();
        destino.setId(1L);
        destino.setSaldo(BigDecimal.valueOf(1000));
        destino.setSaldoDisponible(BigDecimal.valueOf(1000));
        destino.setEstado(EstadoCuenta.ACTIVA);

        TransactionRequest req = new TransactionRequest();
        req.setTipo(TipoTransaccion.DEPOSITO);
        req.setMonto(BigDecimal.valueOf(500));
        req.setProductoDestinoId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(destino));
        when(transactionRepository.save(any(TransactionEntity.class))).thenAnswer(inv -> {
            TransactionEntity e = inv.getArgument(0);
            e.setId(10L);
            return e;
        });

        TransactionResponse resp = service.crearTransaccion(req);

        assertNotNull(resp);
        assertEquals(10L, resp.getId());
        assertEquals(BigDecimal.valueOf(1500), destino.getSaldo());
    }

    @Test
    void shouldThrowExceptionWhenWithdrawInsufficientBalance() {
        ProductEntity origen = new ProductEntity();
        origen.setId(2L);
        origen.setSaldo(BigDecimal.valueOf(100));
        origen.setSaldoDisponible(BigDecimal.valueOf(100));
        origen.setEstado(EstadoCuenta.ACTIVA);

        TransactionRequest req = new TransactionRequest();
        req.setTipo(TipoTransaccion.RETIRO);
        req.setMonto(BigDecimal.valueOf(200));
        req.setProductoOrigenId(2L);

        when(productRepository.findById(2L)).thenReturn(Optional.of(origen));

        BusinessException ex = assertThrows(BusinessException.class, () -> service.crearTransaccion(req));
        assertEquals("Saldo insuficiente para el retiro", ex.getMessage());
    }

    @Test
    void shouldCreateTransferTransaction() {
        ProductEntity origen = new ProductEntity();
        origen.setId(3L);
        origen.setSaldo(BigDecimal.valueOf(1000));
        origen.setSaldoDisponible(BigDecimal.valueOf(1000));
        origen.setEstado(EstadoCuenta.ACTIVA);

        ProductEntity destino = new ProductEntity();
        destino.setId(4L);
        destino.setSaldo(BigDecimal.valueOf(500));
        destino.setSaldoDisponible(BigDecimal.valueOf(500));
        destino.setEstado(EstadoCuenta.ACTIVA);

        TransactionRequest req = new TransactionRequest();
        req.setTipo(TipoTransaccion.TRANSFERENCIA);
        req.setMonto(BigDecimal.valueOf(300));
        req.setProductoOrigenId(3L);
        req.setProductoDestinoId(4L);

        when(productRepository.findById(3L)).thenReturn(Optional.of(origen));
        when(productRepository.findById(4L)).thenReturn(Optional.of(destino));
        when(transactionRepository.save(any(TransactionEntity.class))).thenAnswer(inv -> {
            TransactionEntity e = inv.getArgument(0);
            e.setId(20L);
            return e;
        });

        TransactionResponse resp = service.crearTransaccion(req);

        assertNotNull(resp);
        assertEquals(20L, resp.getId());
        assertEquals(BigDecimal.valueOf(700), origen.getSaldo());
        assertEquals(BigDecimal.valueOf(800), destino.getSaldo());
    }

    @Test
    void shouldThrowNotFoundWhenTransactionDoesNotExist() {
        when(transactionRepository.findById(99L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.obtenerTransaccionPorId(99L));
        assertEquals("Transacción no encontrada", ex.getMessage());
    }
}