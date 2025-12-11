package com.prueba.banco.controller;

import com.prueba.banco.dto.ClientRequest;
import com.prueba.banco.dto.ClientResponse;
import com.prueba.banco.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientResponse> crear(@Valid @RequestBody ClientRequest request) {
        ClientResponse response = clientService.crearCliente(request);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.obtenerClientePorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> listar() {
        return ResponseEntity.ok(clientService.listarClientes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> actualizar(@PathVariable Long id,
                                                     @Valid @RequestBody ClientRequest request) {
        return ResponseEntity.ok(clientService.actualizarCliente(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clientService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}