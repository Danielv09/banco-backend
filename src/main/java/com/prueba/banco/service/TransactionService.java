package com.prueba.banco.service;

import com.prueba.banco.dto.TransactionRequest;
import com.prueba.banco.dto.TransactionResponse;
import java.util.List;

public interface TransactionService {
    TransactionResponse crearTransaccion(TransactionRequest request);
    TransactionResponse obtenerTransaccionPorId(Long id);
    List<TransactionResponse> listarTransacciones();
}