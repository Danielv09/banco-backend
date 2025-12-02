package com.prueba.banco.controller;

import com.prueba.banco.dto.ClientRequest;
import com.prueba.banco.dto.ClientResponse;
import com.prueba.banco.entity.Client;
import com.prueba.banco.mapper.ClientMapper;
import com.prueba.banco.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService service;
    private final ClientMapper mapper;

    public ClientController(ClientService service, ClientMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    // --- Crear cliente ---
    @PostMapping
    public ResponseEntity<ClientResponse> create(@Valid @RequestBody ClientRequest req) {
        Client created = service.create(req);
        return ResponseEntity.ok(mapper.toResponse(created));
    }

    // --- Listar todos los clientes ---
    @GetMapping
    public ResponseEntity<List<ClientResponse>> list() {
        List<ClientResponse> clientes = service.list().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clientes);
    }

    // --- Obtener cliente por ID ---
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getById(@PathVariable Long id) {
        Client cliente = service.getById(id);
        return ResponseEntity.ok(mapper.toResponse(cliente));
    }

    // --- Actualizar cliente ---
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable Long id,
                                                 @Valid @RequestBody ClientRequest req) {
        Client updated = service.update(id, req);
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    // --- Eliminar cliente ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}