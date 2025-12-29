package com.prueba.banco.util.mapper;

import com.prueba.banco.dto.ClientRequest;
import com.prueba.banco.dto.ClientResponse;
import com.prueba.banco.entity.ClientEntity;

public class ClientMapper {

    public static ClientEntity toEntity(ClientRequest request) {
        ClientEntity entity = new ClientEntity();
        entity.setTipoIdentificacion(request.getTipoIdentificacion());
        entity.setNumeroIdentificacion(request.getNumeroIdentificacion());
        entity.setNombre(request.getNombre());
        entity.setApellido(request.getApellido());
        entity.setCorreo(request.getCorreo());
        entity.setFechaNacimiento(request.getFechaNacimiento());
        return entity;
    }

    public static ClientResponse toResponse(ClientEntity entity) {
        ClientResponse response = new ClientResponse();
        response.setId(entity.getId());
        response.setTipoIdentificacion(entity.getTipoIdentificacion());
        response.setNumeroIdentificacion(entity.getNumeroIdentificacion());
        response.setNombre(entity.getNombre());
        response.setApellido(entity.getApellido());
        response.setCorreo(entity.getCorreo());
        response.setFechaNacimiento(entity.getFechaNacimiento());
        response.setFechaCreacion(entity.getFechaCreacion());
        response.setFechaModificacion(entity.getFechaModificacion());
        return response;
    }
}