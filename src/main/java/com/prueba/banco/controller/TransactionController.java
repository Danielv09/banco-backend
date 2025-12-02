package com.prueba.banco.controller;

import com.prueba.banco.dto.TransactionRequest;
import com.prueba.banco.dto.TransactionResponse;
import com.prueba.banco.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // --- Consignación ---
    // POST /api/transactions/deposit
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(@Valid @RequestBody TransactionRequest request) {
        TransactionResponse resp = transactionService.deposit(request);
        return ResponseEntity.ok(resp);
    }

    // --- Retiro ---
    // POST /api/transactions/withdraw
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@Valid @RequestBody TransactionRequest request) {
        TransactionResponse resp = transactionService.withdraw(request);
        return ResponseEntity.ok(resp);
    }

    // --- Transferencia ---
    // POST /api/transactions/transfer
    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransactionRequest request) {
        TransactionResponse resp = transactionService.transfer(request);
        return ResponseEntity.ok(resp);
    }

    // --- Listar transacciones por producto ---
    // GET /api/transactions/product/{productId}
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<TransactionResponse>> listByProduct(@PathVariable Long productId) {
        List<TransactionResponse> list = transactionService.listByProduct(productId);
        return ResponseEntity.ok(list);
    }
}