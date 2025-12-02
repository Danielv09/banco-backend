package com.prueba.banco.service;

import com.prueba.banco.dto.TransactionRequest;
import com.prueba.banco.dto.TransactionResponse;

import java.util.List;

public interface TransactionService {

    // Consignación (depósito en una cuenta)
    TransactionResponse deposit(TransactionRequest req);

    // Retiro (extracción de dinero de una cuenta)
    TransactionResponse withdraw(TransactionRequest req);

    // Transferencia (movimiento entre dos cuentas)
    TransactionResponse transfer(TransactionRequest req);

    // Listar transacciones por producto destino
    List<TransactionResponse> listByProduct(Long productId);
}