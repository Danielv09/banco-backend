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
import com.prueba.banco.util.mapper.ProductMapper;
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

        ProductEntity entity = ProductMapper.toEntity(request, cliente);
        entity.setNumeroCuenta(generarNumeroCuenta(request.getTipoCuenta()));
        entity.setEstado(EstadoCuenta.ACTIVA);

        ProductEntity saved = productRepository.save(entity);
        return ProductMapper.toResponse(saved);
    }

    @Override
    public ProductResponse obtenerProductoPorId(Long id) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
        return ProductMapper.toResponse(entity);
    }

    @Override
    public List<ProductResponse> listarProductos() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponse)
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
        return ProductMapper.toResponse(updated);
    }

    @Override
    public void eliminarProducto(Long id) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));


        if (entity.getSaldoDisponible() != null && entity.getSaldoDisponible().compareTo(BigDecimal.ZERO) > 0) {
            throw new BusinessException("Solo se pueden cancelar cuentas con saldo disponible igual a 0");
        }
        productRepository.delete(entity);
    }

    @Override
    public ProductResponse actualizarEstado(Long id, EstadoCuenta nuevoEstado) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));


        if (nuevoEstado == EstadoCuenta.INACTIVA &&
                entity.getSaldoDisponible() != null &&
                entity.getSaldoDisponible().compareTo(BigDecimal.ZERO) > 0) {
            throw new BusinessException("No se puede inactivar una cuenta con saldo disponible mayor a 0");
        }

        entity.setEstado(nuevoEstado);
        ProductEntity updated = productRepository.save(entity);
        return ProductMapper.toResponse(updated);
    }

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
}