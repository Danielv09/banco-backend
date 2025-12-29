package com.prueba.banco.util.mapper;

import com.prueba.banco.dto.ProductRequest;
import com.prueba.banco.dto.ProductResponse;
import com.prueba.banco.entity.ProductEntity;
import com.prueba.banco.entity.ClientEntity;

public class ProductMapper {


    public static ProductEntity toEntity(ProductRequest request, ClientEntity cliente) {
        ProductEntity entity = new ProductEntity();
        entity.setTipoCuenta(request.getTipoCuenta());
        entity.setSaldo(request.getSaldo());
        entity.setSaldoDisponible(request.getSaldo());
        entity.setCliente(cliente);
        return entity;
    }


    public static ProductResponse toResponse(ProductEntity entity) {
        ProductResponse response = new ProductResponse();
        response.setId(entity.getId());
        response.setNumeroCuenta(entity.getNumeroCuenta());
        response.setTipoCuenta(entity.getTipoCuenta());
        response.setEstado(entity.getEstado());
        response.setSaldo(entity.getSaldo());
        response.setSaldoDisponible(entity.getSaldoDisponible());
        response.setClienteId(entity.getCliente() != null ? entity.getCliente().getId() : null);
        response.setFechaCreacion(entity.getFechaCreacion());
        response.setFechaModificacion(entity.getFechaModificacion());
        return response;
    }
}