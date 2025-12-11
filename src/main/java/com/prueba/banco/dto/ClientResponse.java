package com.prueba.banco.dto;

import com.prueba.banco.entity.enums.TipoIdentificacion;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClientResponse {

    private Long id;
    private TipoIdentificacion tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombre;
    private String apellido;
    private String correo;
    private LocalDate fechaNacimiento;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaCreacion;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaModificacion;

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TipoIdentificacion getTipoIdentificacion() { return tipoIdentificacion; }
    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) { this.tipoIdentificacion = tipoIdentificacion; }

    public String getNumeroIdentificacion() { return numeroIdentificacion; }
    public void setNumeroIdentificacion(String numeroIdentificacion) { this.numeroIdentificacion = numeroIdentificacion; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(LocalDateTime fechaModificacion) { this.fechaModificacion = fechaModificacion; }
}