package com.prueba.banco.repository;

import com.prueba.banco.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Busca todas las transacciones asociadas a una cuenta destino por su ID
    List<Transaction> findByCuentaDestinoId(Long productId);
}