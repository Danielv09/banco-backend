package com.prueba.banco.service;

import com.prueba.banco.dto.ClientRequest;
import com.prueba.banco.entity.Client;

import java.util.List;

public interface ClientService {

    // Crear un nuevo cliente
    Client create(ClientRequest req);

    // Listar todos los clientes
    List<Client> list();

    // Obtener cliente por ID
    Client getById(Long id);

    // Actualizar cliente existente
    Client update(Long id, ClientRequest req);

    // Eliminar cliente por ID
    void delete(Long id);
}