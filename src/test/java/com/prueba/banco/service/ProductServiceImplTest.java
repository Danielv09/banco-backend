package com.prueba.banco.service;

import com.prueba.banco.dto.ProductRequest;
import com.prueba.banco.entity.ClientEntity;
import com.prueba.banco.entity.ProductEntity;
import com.prueba.banco.entity.enums.EstadoCuenta;
import com.prueba.banco.entity.enums.TipoCuenta;
import com.prueba.banco.exception.BusinessException;
import com.prueba.banco.exception.NotFoundException;
import com.prueba.banco.repository.ClientRepository;
import com.prueba.banco.repository.ProductRepository;
import com.prueba.banco.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    private ProductRepository productRepository;
    private ClientRepository clientRepository;
    private ProductServiceImpl service;

    @BeforeEach
    void setup() {
        productRepository = Mockito.mock(ProductRepository.class);
        clientRepository = Mockito.mock(ClientRepository.class);
        service = new ProductServiceImpl(productRepository, clientRepository);
    }

    @Test
    void shouldCreateProductWhenValid() {
        ClientEntity cliente = new ClientEntity();
        cliente.setId(1L);

        ProductRequest req = new ProductRequest();
        req.setTipoCuenta(TipoCuenta.AHORROS);
        req.setSaldo(BigDecimal.valueOf(1000));
        req.setClienteId(1L);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(productRepository.save(any(ProductEntity.class))).thenAnswer(inv -> {
            ProductEntity e = inv.getArgument(0);
            e.setId(10L);
            e.setEstado(EstadoCuenta.ACTIVA);
            return e;
        });

        assertDoesNotThrow(() -> service.crearProducto(req));
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void shouldNotCreateProductWithNegativeBalance() {
        ProductRequest req = new ProductRequest();
        req.setTipoCuenta(TipoCuenta.CORRIENTE);
        req.setSaldo(BigDecimal.valueOf(-50));
        req.setClienteId(1L);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(new ClientEntity()));

        BusinessException ex = assertThrows(BusinessException.class, () -> service.crearProducto(req));
        assertEquals("El saldo no puede ser negativo", ex.getMessage());
    }

    @Test
    void shouldThrowNotFoundWhenClientDoesNotExist() {
        ProductRequest req = new ProductRequest();
        req.setTipoCuenta(TipoCuenta.AHORROS);
        req.setSaldo(BigDecimal.valueOf(100));
        req.setClienteId(99L);

        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.crearProducto(req));
        assertEquals("Cliente no encontrado", ex.getMessage());
    }

    @Test
    void shouldNotDeleteProductWithBalanceDisponible() {
        ProductEntity entity = new ProductEntity();
        entity.setId(5L);
        entity.setSaldoDisponible(BigDecimal.valueOf(200)); // ✅ usar saldoDisponible

        when(productRepository.findById(5L)).thenReturn(Optional.of(entity));

        BusinessException ex = assertThrows(BusinessException.class, () -> service.eliminarProducto(5L));
        assertEquals("Solo se pueden cancelar cuentas con saldo disponible igual a 0", ex.getMessage());
    }

    @Test
    void shouldDeleteProductWithZeroBalanceDisponible() {
        ProductEntity entity = new ProductEntity();
        entity.setId(6L);
        entity.setSaldoDisponible(BigDecimal.ZERO); // ✅ usar saldoDisponible

        when(productRepository.findById(6L)).thenReturn(Optional.of(entity));

        assertDoesNotThrow(() -> service.eliminarProducto(6L));
        verify(productRepository, times(1)).delete(entity);
    }
}