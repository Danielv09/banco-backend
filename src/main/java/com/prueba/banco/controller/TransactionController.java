package com.prueba.banco.controller;

import com.prueba.banco.dto.TransactionRequest;
import com.prueba.banco.dto.TransactionResponse;
import com.prueba.banco.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> crear(@Valid @RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.crearTransaccion(request);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.obtenerTransaccionPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> listar() {
        return ResponseEntity.ok(transactionService.listarTransacciones());
    }
}