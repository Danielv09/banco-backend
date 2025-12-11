package com.prueba.banco.service;

import com.prueba.banco.dto.ClientRequest;
import com.prueba.banco.entity.ClientEntity;
import com.prueba.banco.entity.enums.TipoIdentificacion;
import com.prueba.banco.exception.BusinessException;
import com.prueba.banco.exception.NotFoundException;
import com.prueba.banco.repository.ClientRepository;
import com.prueba.banco.repository.ProductRepository;
import com.prueba.banco.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientServiceImplTest {

    private ClientRepository clientRepository;
    private ProductRepository productRepository;
    private ClientServiceImpl service;

    @BeforeEach
    void setup() {
        clientRepository = mock(ClientRepository.class);
        productRepository = mock(ProductRepository.class);
        service = new ClientServiceImpl(clientRepository, productRepository);
    }

    @Test
    void shouldCreateClientSuccessfully() {
        ClientRequest req = new ClientRequest();
        req.setTipoIdentificacion(TipoIdentificacion.CC);
        req.setNumeroIdentificacion("123456789");
        req.setNombre("Juan");
        req.setApellido("Gomez");
        req.setCorreo("juan@test.com");
        req.setFechaNacimiento(LocalDate.of(1990, 1, 1));

        when(clientRepository.existsByNumeroIdentificacion("123456789")).thenReturn(false);
        when(clientRepository.existsByCorreo("juan@test.com")).thenReturn(false);
        when(clientRepository.save(any(ClientEntity.class))).thenAnswer(inv -> {
            ClientEntity e = inv.getArgument(0);
            e.setId(1L);
            return e;
        });

        assertDoesNotThrow(() -> service.crearCliente(req));
        verify(clientRepository, times(1)).save(any(ClientEntity.class));
    }

    @Test
    void shouldFailWhenClientIsMinor() {
        ClientRequest req = new ClientRequest();
        req.setTipoIdentificacion(TipoIdentificacion.CC);
        req.setNumeroIdentificacion("111111111");
        req.setNombre("Pedro");
        req.setApellido("Infante");
        req.setCorreo("pedro@test.com");
        req.setFechaNacimiento(LocalDate.now().minusYears(10)); // menor de edad

        when(clientRepository.existsByNumeroIdentificacion("111111111")).thenReturn(false);
        when(clientRepository.existsByCorreo("pedro@test.com")).thenReturn(false);

        BusinessException ex = assertThrows(BusinessException.class, () -> service.crearCliente(req));
        assertEquals("El cliente debe ser mayor de edad", ex.getMessage());
    }

    @Test
    void shouldFailWhenBirthDateIsFuture() {
        ClientRequest req = new ClientRequest();
        req.setTipoIdentificacion(TipoIdentificacion.CC);
        req.setNumeroIdentificacion("222222222");
        req.setNombre("Ana");
        req.setApellido("Lopez");
        req.setCorreo("ana@test.com");
        req.setFechaNacimiento(LocalDate.now().plusYears(1)); // futura

        when(clientRepository.existsByNumeroIdentificacion("222222222")).thenReturn(false);
        when(clientRepository.existsByCorreo("ana@test.com")).thenReturn(false);

        BusinessException ex = assertThrows(BusinessException.class, () -> service.crearCliente(req));
        assertEquals("La fecha de nacimiento no puede ser futura", ex.getMessage());
    }

    @Test
    void shouldFailWhenIdentificationAlreadyExists() {
        ClientRequest req = new ClientRequest();
        req.setTipoIdentificacion(TipoIdentificacion.CC);
        req.setNumeroIdentificacion("333333333");
        req.setNombre("Carlos");
        req.setApellido("Perez");
        req.setCorreo("carlos@test.com");
        req.setFechaNacimiento(LocalDate.of(1990, 1, 1));

        when(clientRepository.existsByNumeroIdentificacion("333333333")).thenReturn(true);

        BusinessException ex = assertThrows(BusinessException.class, () -> service.crearCliente(req));
        assertEquals("Ya existe un cliente con ese número de identificación", ex.getMessage());
    }

    @Test
    void shouldFailWhenEmailAlreadyExists() {
        ClientRequest req = new ClientRequest();
        req.setTipoIdentificacion(TipoIdentificacion.CC);
        req.setNumeroIdentificacion("444444444");
        req.setNombre("Lucia");
        req.setApellido("Rios");
        req.setCorreo("correo@test.com");
        req.setFechaNacimiento(LocalDate.of(1990, 1, 1));

        when(clientRepository.existsByNumeroIdentificacion("444444444")).thenReturn(false);
        when(clientRepository.existsByCorreo("correo@test.com")).thenReturn(true);

        BusinessException ex = assertThrows(BusinessException.class, () -> service.crearCliente(req));
        assertEquals("Ya existe un cliente con ese correo", ex.getMessage());
    }

    @Test
    void shouldGetClientById() {
        ClientEntity entity = new ClientEntity();
        entity.setId(10L);
        entity.setNombre("María");

        when(clientRepository.findById(10L)).thenReturn(Optional.of(entity));

        assertEquals(10L, service.obtenerClientePorId(10L).getId());
        verify(clientRepository, times(1)).findById(10L);
    }

    @Test
    void shouldThrowNotFoundWhenClientDoesNotExistOnGet() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.obtenerClientePorId(99L));
        assertEquals("Cliente no encontrado", ex.getMessage());
    }

    @Test
    void shouldListClients() {
        ClientEntity c1 = new ClientEntity(); c1.setId(1L);
        ClientEntity c2 = new ClientEntity(); c2.setId(2L);

        when(clientRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<?> list = service.listarClientes();
        assertEquals(2, list.size());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void shouldFailWhenClientNotFoundOnUpdate() {
        ClientRequest req = new ClientRequest();
        req.setTipoIdentificacion(TipoIdentificacion.CC);
        req.setNumeroIdentificacion("555555555");
        req.setNombre("Nuevo");
        req.setApellido("Nombre");
        req.setCorreo("nuevo@test.com");
        req.setFechaNacimiento(LocalDate.of(1990, 1, 1));

        when(clientRepository.findById(999L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.actualizarCliente(999L, req));
        assertEquals("Cliente no encontrado", ex.getMessage());
    }

    @Test
    void shouldUpdateClientSuccessfully() {
        ClientEntity entity = new ClientEntity();
        entity.setId(5L);
        entity.setCorreo("old@test.com");
        entity.setFechaNacimiento(LocalDate.of(1990, 1, 1));

        ClientRequest req = new ClientRequest();
        req.setTipoIdentificacion(TipoIdentificacion.CC);
        req.setNumeroIdentificacion("555555555");
        req.setNombre("Nuevo");
        req.setApellido("Nombre");
        req.setCorreo("new@test.com");
        req.setFechaNacimiento(LocalDate.of(1995, 5, 5));

        when(clientRepository.findById(5L)).thenReturn(Optional.of(entity));
        when(clientRepository.existsByCorreo("new@test.com")).thenReturn(false);
        when(clientRepository.save(any(ClientEntity.class))).thenAnswer(inv -> inv.getArgument(0));

        assertDoesNotThrow(() -> service.actualizarCliente(5L, req));
        verify(clientRepository, times(1)).save(any(ClientEntity.class));
    }

    @Test
    void shouldFailUpdateWhenNewEmailAlreadyExists() {
        ClientEntity entity = new ClientEntity();
        entity.setId(6L);
        entity.setCorreo("old@test.com");
        entity.setFechaNacimiento(LocalDate.of(1990, 1, 1));

        ClientRequest req = new ClientRequest();
        req.setTipoIdentificacion(TipoIdentificacion.CC);
        req.setNumeroIdentificacion("666666666");
        req.setNombre("Nombre");
        req.setApellido("Apellido");
        req.setCorreo("exists@test.com");
        req.setFechaNacimiento(LocalDate.of(1990, 1, 1));

        when(clientRepository.findById(6L)).thenReturn(Optional.of(entity));
        when(clientRepository.existsByCorreo("exists@test.com")).thenReturn(true);

        BusinessException ex = assertThrows(BusinessException.class, () -> service.actualizarCliente(6L, req));
        assertEquals("Ya existe un cliente con ese correo", ex.getMessage());
    }

    @Test
    void shouldDeleteClientWithoutProducts() {
        ClientEntity entity = new ClientEntity();
        entity.setId(7L);

        when(clientRepository.findById(7L)).thenReturn(Optional.of(entity));
        when(productRepository.countByCliente_Id(7L)).thenReturn(0L);

        assertDoesNotThrow(() -> service.eliminarCliente(7L));
        verify(clientRepository, times(1)).delete(entity);
    }

    @Test
    void shouldFailDeleteClientWithProducts() {
        ClientEntity entity = new ClientEntity();
        entity.setId(8L);

        when(clientRepository.findById(8L)).thenReturn(Optional.of(entity));
        when(productRepository.countByCliente_Id(8L)).thenReturn(2L);

        BusinessException ex = assertThrows(BusinessException.class, () -> service.eliminarCliente(8L));
        assertEquals("No se puede eliminar un cliente con productos vinculados", ex.getMessage());
    }

    @Test
    void shouldThrowNotFoundWhenClientDoesNotExistOnDelete() {
        when(clientRepository.findById(123L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.eliminarCliente(123L));
        assertEquals("Cliente no encontrado", ex.getMessage());
    }
}