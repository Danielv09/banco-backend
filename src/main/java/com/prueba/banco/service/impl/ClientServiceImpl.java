package com.prueba.banco.service.impl;

import com.prueba.banco.dto.ClientRequest;
import com.prueba.banco.dto.ClientResponse;
import com.prueba.banco.entity.ClientEntity;
import com.prueba.banco.exception.BusinessException;
import com.prueba.banco.exception.NotFoundException;
import com.prueba.banco.repository.ClientRepository;
import com.prueba.banco.repository.ProductRepository;
import com.prueba.banco.service.ClientService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public ClientServiceImpl(ClientRepository clientRepository, ProductRepository productRepository) {
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ClientResponse crearCliente(ClientRequest request) {
        if (clientRepository.existsByNumeroIdentificacion(request.getNumeroIdentificacion())) {
            throw new BusinessException("Ya existe un cliente con ese número de identificación");
        }

        if (clientRepository.existsByCorreo(request.getCorreo())) {
            throw new BusinessException("Ya existe un cliente con ese correo");
        }

        validarMayorDeEdad(request.getFechaNacimiento());

        ClientEntity entity = new ClientEntity();
        entity.setTipoIdentificacion(request.getTipoIdentificacion());
        entity.setNumeroIdentificacion(request.getNumeroIdentificacion());
        entity.setNombre(request.getNombre());
        entity.setApellido(request.getApellido());
        entity.setCorreo(request.getCorreo());
        entity.setFechaNacimiento(request.getFechaNacimiento());

        ClientEntity saved = clientRepository.save(entity);
        return mapToResponse(saved);
    }

    @Override
    public ClientResponse obtenerClientePorId(Long id) {
        ClientEntity entity = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        return mapToResponse(entity);
    }

    @Override
    public List<ClientResponse> listarClientes() {
        return clientRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ClientResponse actualizarCliente(Long id, ClientRequest request) {
        ClientEntity entity = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        if (!entity.getCorreo().equalsIgnoreCase(request.getCorreo())) {
            if (clientRepository.existsByCorreo(request.getCorreo())) {
                throw new BusinessException("Ya existe un cliente con ese correo");
            }
        }

        validarMayorDeEdad(request.getFechaNacimiento());

        entity.setTipoIdentificacion(request.getTipoIdentificacion());
        entity.setNumeroIdentificacion(request.getNumeroIdentificacion());
        entity.setNombre(request.getNombre());
        entity.setApellido(request.getApellido());
        entity.setCorreo(request.getCorreo());
        entity.setFechaNacimiento(request.getFechaNacimiento());

        ClientEntity updated = clientRepository.save(entity);
        return mapToResponse(updated);
    }

    @Override
    public void eliminarCliente(Long id) {
        ClientEntity entity = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        long productos = productRepository.countByCliente_Id(id);
        if (productos > 0) {
            throw new BusinessException("No se puede eliminar un cliente con productos vinculados");
        }

        clientRepository.delete(entity);
    }

    private void validarMayorDeEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new BusinessException("La fecha de nacimiento es obligatoria");
        }
        LocalDate hoy = LocalDate.now();
        if (fechaNacimiento.isAfter(hoy)) {
            throw new BusinessException("La fecha de nacimiento no puede ser futura");
        }
        Period edad = Period.between(fechaNacimiento, hoy);
        if (edad.getYears() < 18) {
            throw new BusinessException("El cliente debe ser mayor de edad");
        }
    }

    private ClientResponse mapToResponse(ClientEntity entity) {
        ClientResponse r = new ClientResponse();
        r.setId(entity.getId());
        r.setTipoIdentificacion(entity.getTipoIdentificacion());
        r.setNumeroIdentificacion(entity.getNumeroIdentificacion());
        r.setNombre(entity.getNombre());
        r.setApellido(entity.getApellido());
        r.setCorreo(entity.getCorreo());
        r.setFechaNacimiento(entity.getFechaNacimiento());
        r.setFechaCreacion(entity.getFechaCreacion());
        r.setFechaModificacion(entity.getFechaModificacion());
        return r;
    }
}