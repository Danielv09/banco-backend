package com.prueba.banco.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para crear o actualizar clientes")
public class ClientRequest {

    @NotBlank(message = "El tipo de identificación no puede estar vacío")
    @Schema(example = "CC", description = "Tipo de identificación (CC, TI, Pasaporte, etc.)")
    private String tipoIdentificacion;

    @NotBlank(message = "El número de identificación no puede estar vacío")
    @Schema(example = "1234567890", description = "Número único de identificación")
    private String numeroIdentificacion;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    @Schema(example = "Juan", description = "Nombre del cliente")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 2, message = "El apellido debe tener al menos 2 caracteres")
    @Schema(example = "Pérez", description = "Apellido del cliente")
    private String apellido;

    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo no tiene un formato válido")
    @Schema(example = "juan.perez@email.com", description = "Correo electrónico único")
    private String correo;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Schema(example = "1990-05-15", description = "Fecha de nacimiento en formato YYYY-MM-DD")
    private LocalDate fechaNacimiento;


    public ClientRequest() {}


    public ClientRequest(String tipoIdentificacion, String numeroIdentificacion,
                         String nombre, String apellido, String correo,
                         LocalDate fechaNacimiento) {
        this.tipoIdentificacion = tipoIdentificacion;
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters y Setters
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
}