package com.prueba.banco.repository;

import com.prueba.banco.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    long countByCliente_Id(Long clienteId);
    boolean existsByNumeroCuenta(String numeroCuenta);
}