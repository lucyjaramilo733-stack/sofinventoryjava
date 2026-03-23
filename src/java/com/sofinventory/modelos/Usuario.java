package com.sofinventory.modelos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Usuario {

    private int    id;
    private int    tipoDocumentoId;
    private String tipoDocumentoNombre;
    private String numeroDocumento;
    private String nombreCompleto;
    private String email;
    private String username;
    private String password;
    private int    rolId;
    private String rolNombre;
    private String estado;
    private LocalDate     fechaCreacion;
    private LocalDateTime fechaRegistro;
    private String observaciones;

    public Usuario() {}

    // Getters y Setters
    public int getId()                          { return id; }
    public void setId(int id)                   { this.id = id; }

    public int getTipoDocumentoId()             { return tipoDocumentoId; }
    public void setTipoDocumentoId(int v)       { this.tipoDocumentoId = v; }

    public String getTipoDocumentoNombre()      { return tipoDocumentoNombre; }
    public void setTipoDocumentoNombre(String v){ this.tipoDocumentoNombre = v; }

    public String getNumeroDocumento()          { return numeroDocumento; }
    public void setNumeroDocumento(String v)    { this.numeroDocumento = v; }

    public String getNombreCompleto()           { return nombreCompleto; }
    public void setNombreCompleto(String v)     { this.nombreCompleto = v; }

    public String getEmail()                    { return email; }
    public void setEmail(String v)              { this.email = v; }

    public String getUsername()                 { return username; }
    public void setUsername(String v)           { this.username = v; }

    public String getPassword()                 { return password; }
    public void setPassword(String v)           { this.password = v; }

    public int getRolId()                       { return rolId; }
    public void setRolId(int v)                 { this.rolId = v; }

    public String getRolNombre()                { return rolNombre; }
    public void setRolNombre(String v)          { this.rolNombre = v; }

    public String getEstado()                   { return estado; }
    public void setEstado(String v)             { this.estado = v; }

    public LocalDate getFechaCreacion()         { return fechaCreacion; }
    public void setFechaCreacion(LocalDate v)   { this.fechaCreacion = v; }

    public LocalDateTime getFechaRegistro()     { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime v){ this.fechaRegistro = v; }

    public String getObservaciones()            { return observaciones; }
    public void setObservaciones(String v)      { this.observaciones = v; }
}
