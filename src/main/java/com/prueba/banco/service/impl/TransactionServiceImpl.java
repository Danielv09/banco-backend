package com.prueba.banco.service.impl;

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
import com.prueba.banco.service.TransactionService;
import com.prueba.banco.util.mapper.TransactionMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  ProductRepository productRepository) {
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
    }

    @Override
    public TransactionResponse crearTransaccion(TransactionRequest request) {
        validarRequest(request);

        TransactionEntity entity = new TransactionEntity();
        entity.setTipo(request.getTipo());
        entity.setMonto(request.getMonto());

        if (request.getTipo() == TipoTransaccion.DEPOSITO) {
            // Ignorar productoOrigenId si viene en el request
            request.setProductoOrigenId(null);

            ProductEntity destino = productRepository.findById(request.getProductoDestinoId())
                    .orElseThrow(() -> new NotFoundException("Cuenta destino no encontrada"));
            validarProductoActivo(destino);

            destino.setSaldo(destino.getSaldo().add(request.getMonto()));
            destino.setSaldoDisponible(destino.getSaldoDisponible().add(request.getMonto()));
            productRepository.save(destino);

            entity.setProductoDestino(destino);
            entity.setSaldoDestino(destino.getSaldo());
            entity.setSaldoDisponibleDestino(destino.getSaldoDisponible());

        } else if (request.getTipo() == TipoTransaccion.RETIRO) {
            ProductEntity origen = productRepository.findById(request.getProductoOrigenId())
                    .orElseThrow(() -> new NotFoundException("Cuenta origen no encontrada"));
            validarProductoActivo(origen);

            if (origen.getSaldoDisponible().compareTo(request.getMonto()) < 0) {
                throw new BusinessException("Saldo insuficiente para el retiro");
            }

            origen.setSaldo(origen.getSaldo().subtract(request.getMonto()));
            origen.setSaldoDisponible(origen.getSaldoDisponible().subtract(request.getMonto()));
            productRepository.save(origen);

            entity.setProductoOrigen(origen);
            entity.setSaldoOrigen(origen.getSaldo());
            entity.setSaldoDisponibleOrigen(origen.getSaldoDisponible());

        } else if (request.getTipo() == TipoTransaccion.TRANSFERENCIA) {
            ProductEntity origen = productRepository.findById(request.getProductoOrigenId())
                    .orElseThrow(() -> new NotFoundException("Cuenta origen no encontrada"));
            ProductEntity destino = productRepository.findById(request.getProductoDestinoId())
                    .orElseThrow(() -> new NotFoundException("Cuenta destino no encontrada"));

            validarProductoActivo(origen);
            validarProductoActivo(destino);

            if (origen.getSaldoDisponible().compareTo(request.getMonto()) < 0) {
                throw new BusinessException("Saldo insuficiente para la transferencia");
            }

            origen.setSaldo(origen.getSaldo().subtract(request.getMonto()));
            origen.setSaldoDisponible(origen.getSaldoDisponible().subtract(request.getMonto()));

            destino.setSaldo(destino.getSaldo().add(request.getMonto()));
            destino.setSaldoDisponible(destino.getSaldoDisponible().add(request.getMonto()));

            productRepository.save(origen);
            productRepository.save(destino);

            entity.setProductoOrigen(origen);
            entity.setProductoDestino(destino);
            entity.setSaldoOrigen(origen.getSaldo());
            entity.setSaldoDisponibleOrigen(origen.getSaldoDisponible());
            entity.setSaldoDestino(destino.getSaldo());
            entity.setSaldoDisponibleDestino(destino.getSaldoDisponible());
        }

        TransactionEntity saved = transactionRepository.save(entity);
        return TransactionMapper.toResponse(saved);
    }

    @Override
    public TransactionResponse obtenerTransaccionPorId(Long id) {
        TransactionEntity entity = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transacción no encontrada"));
        return TransactionMapper.toResponse(entity);
    }

    @Override
    public List<TransactionResponse> listarTransacciones() {
        return transactionRepository.findAll()
                .stream()
                .map(TransactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    private void validarRequest(TransactionRequest request) {
        if (request.getTipo() == null) {
            throw new BusinessException("El tipo de transacción es obligatorio");
        }
        if (request.getMonto() == null || request.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("El monto debe ser mayor a cero");
        }

        if (request.getTipo() == TipoTransaccion.DEPOSITO) {
            if (request.getProductoDestinoId() == null) {
                throw new BusinessException("La cuenta destino es obligatoria para una consignación");
            }
            if (request.getProductoOrigenId() != null) {
                throw new BusinessException("En una consignación no debe enviarse cuenta origen");
            }
        } else if (request.getTipo() == TipoTransaccion.RETIRO) {
            if (request.getProductoOrigenId() == null) {
                throw new BusinessException("La cuenta origen es obligatoria para un retiro");
            }
        } else if (request.getTipo() == TipoTransaccion.TRANSFERENCIA) {
            if (request.getProductoOrigenId() == null || request.getProductoDestinoId() == null) {
                throw new BusinessException("La transferencia requiere cuenta origen y destino");
            }
            if (request.getProductoOrigenId().equals(request.getProductoDestinoId())) {
                throw new BusinessException("La cuenta origen y destino no pueden ser la misma");
            }
        }
    }

    private void validarProductoActivo(ProductEntity producto) {
        if (producto.getEstado() != EstadoCuenta.ACTIVA) {
            throw new BusinessException("No se puede operar con una cuenta " + producto.getEstado());
        }
    }
}