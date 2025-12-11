package com.prueba.banco.service.impl;

import com.prueba.banco.dto.ProductRequest;
import com.prueba.banco.dto.ProductResponse;
import com.prueba.banco.entity.ClientEntity;
import com.prueba.banco.entity.ProductEntity;
import com.prueba.banco.entity.enums.EstadoCuenta;
import com.prueba.banco.entity.enums.TipoCuenta;
import com.prueba.banco.exception.BusinessException;
import com.prueba.banco.exception.NotFoundException;
import com.prueba.banco.repository.ClientRepository;
import com.prueba.banco.repository.ProductRepository;
import com.prueba.banco.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    public ProductServiceImpl(ProductRepository productRepository, ClientRepository clientRepository) {
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public ProductResponse crearProducto(ProductRequest request) {
        ClientEntity cliente = clientRepository.findById(request.getClienteId())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        validarSaldoNoNegativo(request.getSaldo());

        ProductEntity entity = new ProductEntity();
        entity.setNumeroCuenta(generarNumeroCuenta(request.getTipoCuenta()));
        entity.setTipoCuenta(request.getTipoCuenta());

        // Regla: cuentas se crean activas por defecto
        entity.setEstado(EstadoCuenta.ACTIVA);

        entity.setSaldo(request.getSaldo());
        entity.setSaldoDisponible(request.getSaldo());
        entity.setCliente(cliente);

        ProductEntity saved = productRepository.save(entity);
        return mapToResponse(saved);
    }

    @Override
    public ProductResponse obtenerProductoPorId(Long id) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
        return mapToResponse(entity);
    }

    @Override
    public List<ProductResponse> listarProductos() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse actualizarProducto(Long id, ProductRequest request) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        ClientEntity cliente = clientRepository.findById(request.getClienteId())
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        validarSaldoNoNegativo(request.getSaldo());

        entity.setTipoCuenta(request.getTipoCuenta());
        entity.setSaldo(request.getSaldo());
        entity.setSaldoDisponible(request.getSaldo());
        entity.setCliente(cliente);

        ProductEntity updated = productRepository.save(entity);
        return mapToResponse(updated);
    }

    @Override
    public void eliminarProducto(Long id) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        if (entity.getSaldo() != null && entity.getSaldo().compareTo(BigDecimal.ZERO) > 0) {
            throw new BusinessException("Solo se pueden cancelar cuentas con saldo igual a 0");
        }
        productRepository.delete(entity);
    }

    @Override
    public ProductResponse actualizarEstado(Long id, EstadoCuenta nuevoEstado) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));


        if (nuevoEstado == EstadoCuenta.INACTIVA && entity.getSaldo().compareTo(BigDecimal.ZERO) > 0) {
            throw new BusinessException("No se puede inactivar una cuenta con saldo mayor a 0");
        }

        entity.setEstado(nuevoEstado);
        ProductEntity updated = productRepository.save(entity);
        return mapToResponse(updated);
    }

    // Generador de número de cuenta con prefijo y 10 dígitos
    private String generarNumeroCuenta(TipoCuenta tipoCuenta) {
        String prefijo = tipoCuenta == TipoCuenta.AHORROS ? "53" : "33";
        String numeroCuenta;
        Random random = new Random();

        do {
            StringBuilder sb = new StringBuilder(prefijo);
            for (int i = 0; i < 8; i++) {
                sb.append(random.nextInt(10));
            }
            numeroCuenta = sb.toString();
        } while (productRepository.existsByNumeroCuenta(numeroCuenta));

        return numeroCuenta;
    }

    private void validarSaldoNoNegativo(BigDecimal saldo) {
        if (saldo == null) {
            throw new BusinessException("El saldo es obligatorio");
        }
        if (saldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("El saldo no puede ser negativo");
        }
    }

    private ProductResponse mapToResponse(ProductEntity entity) {
        ProductResponse r = new ProductResponse();
        r.setId(entity.getId());
        r.setNumeroCuenta(entity.getNumeroCuenta());
        r.setTipoCuenta(entity.getTipoCuenta());
        r.setEstado(entity.getEstado());
        r.setSaldo(entity.getSaldo());
        r.setSaldoDisponible(entity.getSaldoDisponible());
        r.setClienteId(entity.getCliente() != null ? entity.getCliente().getId() : null);
        r.setFechaCreacion(entity.getFechaCreacion());
        r.setFechaModificacion(entity.getFechaModificacion());
        return r;
    }
}