package com.prueba.banco.mapper;

import com.prueba.banco.dto.TransactionRequest;
import com.prueba.banco.dto.TransactionResponse;
import com.prueba.banco.entity.Product;
import com.prueba.banco.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {


    public Transaction toEntity(TransactionRequest req, Product origin, Product destination) {
        Transaction tx = new Transaction();
        tx.setTipo(req.getTipo());
        tx.setMonto(req.getMonto());
        tx.setCuentaOrigen(origin);
        tx.setCuentaDestino(destination);
        return tx;
    }


    public TransactionResponse toResponse(Transaction entity) {
        TransactionResponse dto = new TransactionResponse();
        dto.setId(entity.getId());
        dto.setTipo(entity.getTipo());
        dto.setMonto(entity.getMonto());
        dto.setFechaTransaccion(entity.getFechaTransaccion());
        dto.setCuentaOrigenId(entity.getCuentaOrigen() != null ? entity.getCuentaOrigen().getId() : null);
        dto.setCuentaDestinoId(entity.getCuentaDestino() != null ? entity.getCuentaDestino().getId() : null);
        dto.setSaldoDisponible(entity.getSaldoDisponible());
        return dto;
    }
}