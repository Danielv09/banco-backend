package com.prueba.banco.repository;

import com.prueba.banco.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByNumeroIdentificacion(String numeroIdentificacion);
    Optional<ClientEntity> findByCorreo(String correo);
    boolean existsByNumeroIdentificacion(String numeroIdentificacion);
    boolean existsByCorreo(String correo);
}