package com.prueba.banco.mapper;

import com.prueba.banco.dto.ClientRequest;
import com.prueba.banco.dto.ClientResponse;
import com.prueba.banco.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    // Convierte un ClientRequest (entrada del usuario) en una entity Client
    public Client toEntity(ClientRequest req) {
        Client c = new Client();
        c.setTipoIdentificacion(req.getTipoIdentificacion());
        c.setNumeroIdentificacion(req.getNumeroIdentificacion());
        c.setNombre(req.getNombre());
        c.setApellido(req.getApellido());
        c.setCorreo(req.getCorreo());
        c.setFechaNacimiento(req.getFechaNacimiento());
        return c;
    }

    // Actualiza una entity existente con los datos de un ClientRequest
    public void updateEntity(Client target, ClientRequest req) {
        target.setTipoIdentificacion(req.getTipoIdentificacion());
        target.setNumeroIdentificacion(req.getNumeroIdentificacion());
        target.setNombre(req.getNombre());
        target.setApellido(req.getApellido());
        target.setCorreo(req.getCorreo());
        target.setFechaNacimiento(req.getFechaNacimiento());
    }

    // Convierte una entity Client en un ClientResponse (salida hacia el usuario)
    public ClientResponse toResponse(Client c) {
        ClientResponse r = new ClientResponse();
        r.setId(c.getId());
        r.setTipoIdentificacion(c.getTipoIdentificacion());
        r.setNumeroIdentificacion(c.getNumeroIdentificacion());
        r.setNombre(c.getNombre());
        r.setApellido(c.getApellido());
        r.setCorreo(c.getCorreo());
        r.setFechaNacimiento(c.getFechaNacimiento());
        r.setFechaCreacion(c.getFechaCreacion());
        r.setFechaModificacion(c.getFechaModificacion());
        return r;
    }
}