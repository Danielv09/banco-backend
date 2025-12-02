package com.prueba.banco.mapper;

import com.prueba.banco.dto.ProductRequest;
import com.prueba.banco.dto.ProductResponse;
import com.prueba.banco.entity.Client;
import com.prueba.banco.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    // Convierte un request en una entidad Product
    public Product toEntity(ProductRequest req, Client cliente) {
        Product p = new Product();
        p.setTipoCuenta(req.getTipoCuenta());
        p.setSaldo(req.getSaldo());
        p.setExentaGMF(Boolean.TRUE.equals(req.getExentaGMF()));
        p.setCliente(cliente);
        return p;
    }

    // Actualiza una entidad existente con datos del request
    public void updateEntity(Product target, ProductRequest req, Client cliente) {
        if (req.getTipoCuenta() != null) target.setTipoCuenta(req.getTipoCuenta());
        if (req.getSaldo() != null) target.setSaldo(req.getSaldo());
        if (req.getExentaGMF() != null) target.setExentaGMF(req.getExentaGMF());
        if (cliente != null) target.setCliente(cliente);
    }

    // Convierte una entidad en respuesta
    public ProductResponse toResponse(Product entity) {
        ProductResponse dto = new ProductResponse();
        dto.setId(entity.getId());
        dto.setTipoCuenta(entity.getTipoCuenta());
        dto.setNumeroCuenta(entity.getNumeroCuenta());
        dto.setEstado(entity.getEstado());
        dto.setSaldo(entity.getSaldo());
        dto.setExentaGMF(entity.getExentaGMF());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaModificacion(entity.getFechaModificacion());
        dto.setClienteId(entity.getCliente() != null ? entity.getCliente().getId() : null);
        return dto;
    }
}