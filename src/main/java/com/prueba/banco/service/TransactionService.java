package com.prueba.banco.service;

import com.prueba.banco.dto.TransactionRequest;
import com.prueba.banco.dto.TransactionResponse;

import java.util.List;

public interface TransactionService {

    // Consignación
    TransactionResponse deposit(TransactionRequest req);

    // Retiro
    TransactionResponse withdraw(TransactionRequest req);

    // Transferencia
    TransactionResponse transfer(TransactionRequest req);

    // Listar
    List<TransactionResponse> listByProduct(Long productId);
}