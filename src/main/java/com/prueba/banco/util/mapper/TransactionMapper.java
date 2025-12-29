package com.prueba.banco.util.mapper;

import com.prueba.banco.dto.TransactionResponse;
import com.prueba.banco.entity.TransactionEntity;

public class TransactionMapper {


    public static TransactionResponse toResponse(TransactionEntity entity) {
        TransactionResponse response = new TransactionResponse();
        response.setId(entity.getId());
        response.setTipo(entity.getTipo());
        response.setMonto(entity.getMonto());
        response.setProductoOrigenId(entity.getProductoOrigen() != null ? entity.getProductoOrigen().getId() : null);
        response.setProductoDestinoId(entity.getProductoDestino() != null ? entity.getProductoDestino().getId() : null);
        response.setFecha(entity.getFecha());


        response.setSaldoOrigen(entity.getSaldoOrigen());
        response.setSaldoDisponibleOrigen(entity.getSaldoDisponibleOrigen());
        response.setSaldoDestino(entity.getSaldoDestino());
        response.setSaldoDisponibleDestino(entity.getSaldoDisponibleDestino());

        return response;
    }
}