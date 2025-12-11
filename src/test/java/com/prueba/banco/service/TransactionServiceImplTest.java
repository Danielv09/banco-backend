package com.prueba.banco.service;

import com.prueba.banco.dto.TransactionRequest;
import com.prueba.banco.entity.ProductEntity;
import com.prueba.banco.entity.TransactionEntity;
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
    void shouldDepositSuccessfully() {
        ProductEntity destino = new ProductEntity();
        destino.setId(1L);
        destino.setSaldo(BigDecimal.valueOf(100));

        TransactionRequest req = new TransactionRequest();
        req.setTipo(TipoTransaccion.CONSIGNACION);
        req.setMonto(BigDecimal.valueOf(50));
        req.setProductoDestinoId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(destino));
        when(transactionRepository.save(any(TransactionEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        assertDoesNotThrow(() -> service.crearTransaccion(req));
        assertEquals(BigDecimal.valueOf(150), destino.getSaldo());
    }

    @Test
    void shouldFailWithdrawalWithInsufficientBalance() {
        ProductEntity origen = new ProductEntity();
        origen.setId(2L);
        origen.setSaldo(BigDecimal.valueOf(30));

        TransactionRequest req = new TransactionRequest();
        req.setTipo(TipoTransaccion.RETIRO);
        req.setMonto(BigDecimal.valueOf(50));
        req.setProductoOrigenId(2L);

        when(productRepository.findById(2L)).thenReturn(Optional.of(origen));

        BusinessException ex = assertThrows(BusinessException.class, () -> service.crearTransaccion(req));
        assertEquals("Saldo insuficiente para el retiro", ex.getMessage());
    }

    @Test
    void shouldTransferSuccessfully() {
        ProductEntity origen = new ProductEntity();
        origen.setId(3L);
        origen.setSaldo(BigDecimal.valueOf(200));

        ProductEntity destino = new ProductEntity();
        destino.setId(4L);
        destino.setSaldo(BigDecimal.valueOf(100));

        TransactionRequest req = new TransactionRequest();
        req.setTipo(TipoTransaccion.TRANSFERENCIA);
        req.setMonto(BigDecimal.valueOf(50));
        req.setProductoOrigenId(3L);
        req.setProductoDestinoId(4L);

        when(productRepository.findById(3L)).thenReturn(Optional.of(origen));
        when(productRepository.findById(4L)).thenReturn(Optional.of(destino));
        when(transactionRepository.save(any(TransactionEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        service.crearTransaccion(req);

        assertEquals(BigDecimal.valueOf(150), origen.getSaldo());
        assertEquals(BigDecimal.valueOf(150), destino.getSaldo());
    }

    @Test
    void shouldFailTransferToSameAccount() {
        ProductEntity origen = new ProductEntity();
        origen.setId(5L);
        origen.setSaldo(BigDecimal.valueOf(100));

        TransactionRequest req = new TransactionRequest();
        req.setTipo(TipoTransaccion.TRANSFERENCIA);
        req.setMonto(BigDecimal.valueOf(50));
        req.setProductoOrigenId(5L);
        req.setProductoDestinoId(5L);

        when(productRepository.findById(5L)).thenReturn(Optional.of(origen));

        BusinessException ex = assertThrows(BusinessException.class, () -> service.crearTransaccion(req));
        assertEquals("La cuenta origen y destino no pueden ser la misma", ex.getMessage());
    }

    @Test
    void shouldThrowNotFoundWhenAccountDoesNotExist() {
        TransactionRequest req = new TransactionRequest();
        req.setTipo(TipoTransaccion.CONSIGNACION);
        req.setMonto(BigDecimal.valueOf(50));
        req.setProductoDestinoId(99L);

        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.crearTransaccion(req));
        assertEquals("Cuenta destino no encontrada", ex.getMessage());
    }
}