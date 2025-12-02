package com.prueba.banco.repository;

import com.prueba.banco.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByNumeroIdentificacion(String numeroIdentificacion);
    Optional<Client> findByCorreo(String correo);
    boolean existsByNumeroIdentificacion(String numeroIdentificacion);
    boolean existsByCorreo(String correo);
}