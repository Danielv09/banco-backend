package com.prueba.banco.service.impl;

import com.prueba.banco.dto.ClientRequest;
import com.prueba.banco.entity.Client;
import com.prueba.banco.exception.BusinessRuleException;
import com.prueba.banco.exception.ClientNotFoundException;
import com.prueba.banco.mapper.ClientMapper;
import com.prueba.banco.repository.ClientRepository;
import com.prueba.banco.repository.ProductRepository;
import com.prueba.banco.service.ClientService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final ProductRepository productRepository;
    private final ClientMapper mapper;

    public ClientServiceImpl(ClientRepository repository,
                             ProductRepository productRepository,
                             ClientMapper mapper) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public Client create(ClientRequest req) {
        validateAdult(req.getFechaNacimiento());

        if (repository.existsByNumeroIdentificacion(req.getNumeroIdentificacion())) {
            throw new BusinessRuleException("Ya existe un cliente con ese número de identificación");
        }
        if (repository.existsByCorreo(req.getCorreo())) {
            throw new BusinessRuleException("Ya existe un cliente con ese correo");
        }

        Client entity = mapper.toEntity(req);
        return repository.save(entity);
    }

    @Override
    public List<Client> list() {
        return repository.findAll();
    }

    @Override
    public Client getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    @Override
    public Client update(Long id, ClientRequest req) {
        validateAdult(req.getFechaNacimiento());
        Client existing = getById(id);

        repository.findByNumeroIdentificacion(req.getNumeroIdentificacion())
                .filter(c -> !c.getId().equals(id))
                .ifPresent(c -> { throw new BusinessRuleException("Número de identificación ya está en uso"); });

        repository.findByCorreo(req.getCorreo())
                .filter(c -> !c.getId().equals(id))
                .ifPresent(c -> { throw new BusinessRuleException("Correo ya está en uso"); });

        mapper.updateEntity(existing, req);
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        Client existing = getById(id);

        if (productRepository.existsByClienteId(id)) {
            throw new BusinessRuleException("No se puede eliminar el cliente: tiene productos vinculados");
        }

        repository.delete(existing);
    }


    private void validateAdult(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new BusinessRuleException("La fecha de nacimiento es obligatoria");
        }
        int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        if (edad < 18) {
            throw new BusinessRuleException("El cliente debe ser mayor de edad (≥ 18 años)");
        }
    }
}