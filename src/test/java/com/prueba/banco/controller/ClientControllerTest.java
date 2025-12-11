package com.prueba.banco.controller;

import com.prueba.banco.dto.ClientRequest;
import com.prueba.banco.dto.ClientResponse;
import com.prueba.banco.entity.enums.TipoIdentificacion;
import com.prueba.banco.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientControllerTest {

    @Test
    void shouldReturn201OnCreateClient() {
        ClientService service = mock(ClientService.class);
        ClientController controller = new ClientController(service);

        ClientRequest req = new ClientRequest();
        req.setTipoIdentificacion(TipoIdentificacion.CC);
        req.setNumeroIdentificacion("123456789");
        req.setNombre("Daniel");
        req.setApellido("Perez");
        req.setCorreo("daniel@test.com");
        req.setFechaNacimiento(LocalDate.of(1990, 1, 1));

        ClientResponse resp = new ClientResponse();
        resp.setId(1L);
        resp.setTipoIdentificacion(TipoIdentificacion.CC);
        resp.setNumeroIdentificacion("123456789");
        resp.setNombre("Daniel");
        resp.setApellido("Perez");
        resp.setCorreo("daniel@test.com");
        resp.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        resp.setFechaCreacion(LocalDateTime.now());

        when(service.crearCliente(req)).thenReturn(resp);

        ResponseEntity<ClientResponse> response = controller.crear(req);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
        assertEquals(TipoIdentificacion.CC, response.getBody().getTipoIdentificacion());
    }

    @Test
    void shouldReturn200OnGetClient() {
        ClientService service = mock(ClientService.class);
        ClientController controller = new ClientController(service);

        ClientResponse resp = new ClientResponse();
        resp.setId(2L);
        resp.setTipoIdentificacion(TipoIdentificacion.TI);
        resp.setNumeroIdentificacion("987654321");
        resp.setNombre("Maria");
        resp.setApellido("Lopez");
        resp.setCorreo("maria@test.com");
        resp.setFechaNacimiento(LocalDate.of(1995, 5, 10));
        resp.setFechaCreacion(LocalDateTime.now());

        when(service.obtenerClientePorId(2L)).thenReturn(resp);

        ResponseEntity<ClientResponse> response = controller.obtener(2L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Maria", response.getBody().getNombre());
        assertEquals(TipoIdentificacion.TI, response.getBody().getTipoIdentificacion());
    }
}