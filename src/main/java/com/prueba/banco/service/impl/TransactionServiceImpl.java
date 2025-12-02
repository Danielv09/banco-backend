package com.prueba.banco.service.impl;

import com.prueba.banco.dto.TransactionRequest;
import com.prueba.banco.dto.TransactionResponse;
import com.prueba.banco.entity.Product;
import com.prueba.banco.entity.Transaction;
import com.prueba.banco.exception.BusinessRuleException;
import com.prueba.banco.exception.ProductNotFoundException;
import com.prueba.banco.mapper.TransactionMapper;
import com.prueba.banco.repository.ProductRepository;
import com.prueba.banco.repository.TransactionRepository;
import com.prueba.banco.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final TransactionMapper mapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  ProductRepository productRepository,
                                  TransactionMapper mapper) {
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    // --- Consignación ---
    @Override
    public TransactionResponse deposit(TransactionRequest req) {
        Product destination = productRepository.findById(req.getCuentaDestinoId())
                .orElseThrow(() -> new ProductNotFoundException(req.getCuentaDestinoId()));

        // Actualizar saldo
        destination.setSaldo(destination.getSaldo().add(req.getMonto()));
        productRepository.save(destination);

        // Registrar transacción
        Transaction tx = mapper.toEntity(req, null, destination);
        tx.setSaldoDisponible(destination.getSaldo());
        return mapper.toResponse(transactionRepository.save(tx));
    }

    // --- Retiro ---
    @Override
    public TransactionResponse withdraw(TransactionRequest req) {
        Product origin = productRepository.findById(req.getCuentaOrigenId())
                .orElseThrow(() -> new ProductNotFoundException(req.getCuentaOrigenId()));

        // Validar saldo suficiente
        if (origin.getSaldo().compareTo(req.getMonto()) < 0) {
            throw new BusinessRuleException("Saldo insuficiente para realizar el retiro");
        }

        // Actualizar saldo
        origin.setSaldo(origin.getSaldo().subtract(req.getMonto()));
        productRepository.save(origin);

        // Registrar transacción
        Transaction tx = mapper.toEntity(req, origin, null);
        tx.setSaldoDisponible(origin.getSaldo());
        return mapper.toResponse(transactionRepository.save(tx));
    }

    // --- Transferencia ---
    @Override
    public TransactionResponse transfer(TransactionRequest req) {
        Product origin = productRepository.findById(req.getCuentaOrigenId())
                .orElseThrow(() -> new ProductNotFoundException(req.getCuentaOrigenId()));
        Product destination = productRepository.findById(req.getCuentaDestinoId())
                .orElseThrow(() -> new ProductNotFoundException(req.getCuentaDestinoId()));

        // Validar saldo suficiente
        if (origin.getSaldo().compareTo(req.getMonto()) < 0) {
            throw new BusinessRuleException("Saldo insuficiente para realizar la transferencia");
        }

        // Débito en origen
        origin.setSaldo(origin.getSaldo().subtract(req.getMonto()));
        productRepository.save(origin);

        // Crédito en destino
        destination.setSaldo(destination.getSaldo().add(req.getMonto()));
        productRepository.save(destination);

        // Registrar transacción
        Transaction tx = mapper.toEntity(req, origin, destination);
        tx.setSaldoDisponible(origin.getSaldo());
        return mapper.toResponse(transactionRepository.save(tx));
    }

    // --- Listar transacciones por producto destino ---
    @Override
    public List<TransactionResponse> listByProduct(Long productId) {
        return transactionRepository.findByCuentaDestinoId(productId).stream()
                .map(mapper::toResponse)
                .toList();
    }
}