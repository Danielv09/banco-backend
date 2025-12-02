package com.prueba.banco.repository;

import com.prueba.banco.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    List<Transaction> findByCuentaDestinoId(Long productId);
}