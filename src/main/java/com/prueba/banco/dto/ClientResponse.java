package com.prueba.banco.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de respuesta para clientes")
public class ClientResponse {

    @Schema(example = "1", description = "Identificador único del cliente")
    private Long id;

    @Schema(example = "CC", description = "Tipo de identificación (CC, TI, Pasaporte, etc.)")
    private String tipoIdentificacion;

    @Schema(example = "1234567890", description = "Número único de identificación")
    private String numeroIdentificacion;

    @Schema(example = "Juan", description = "Nombre del cliente")
    private String nombre;

    @Schema(example = "Pérez", description = "Apellido del cliente")
    private String apellido;

    @Schema(example = "juan.perez@email.com", description = "Correo electrónico único")
    private String correo;

    @Schema(example = "1990-05-15", description = "Fecha de nacimiento en formato YYYY-MM-DD")
    private LocalDate fechaNacimiento;

    @Schema(example = "2025-11-30T15:30:00", description = "Fecha de creación del registro")
    private LocalDateTime fechaCreacion;

    @Schema(example = "2025-11-30T15:45:00", description = "Fecha de última modificación del registro")
    private LocalDateTime fechaModificacion;

    // --- Constructor vacío ---
    public ClientResponse() {}

    // --- Constructor completo ---
    public ClientResponse(Long id, String tipoIdentificacion, String numeroIdentificacion,
                          String nombre, String apellido, String correo,
                          LocalDate fechaNacimiento, LocalDateTime fechaCreacion, LocalDateTime fechaModificacion) {
        this.id = id;
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipoIdentificacion() { return tipoIdentificacion; }
    public void setTipoIdentificacion(String tipoIdentificacion) { this.tipoIdentificacion = tipoIdentificacion; }

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