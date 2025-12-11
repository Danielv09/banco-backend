package com.prueba.banco.service;

import com.prueba.banco.dto.ClientRequest;
import com.prueba.banco.dto.ClientResponse;
import java.util.List;

public interface ClientService {
    ClientResponse crearCliente(ClientRequest request);
    ClientResponse obtenerClientePorId(Long id);
    List<ClientResponse> listarClientes();
    ClientResponse actualizarCliente(Long id, ClientRequest request);
    void eliminarCliente(Long id);
}