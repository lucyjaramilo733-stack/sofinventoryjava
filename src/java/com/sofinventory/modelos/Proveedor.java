package com.sofinventory.modelos;

import java.time.LocalDateTime;

public class Proveedor {

    private int    id;
    private int    tipoDocumentoId;
    private String tipoDocumentoNombre;
    private String numeroDocumento;
    private String razonSocial;
    private String nombreContacto;
    private String cargoContacto;
    private String email;
    private String telefono;
    private String direccion;
    private String pais;
    private String departamento;
    private String ciudad;
    private String tipoProveedor;
    private String estado;
    private String observaciones;
    private int    creadoPorId;
    private String creadoPorNombre;
    private LocalDateTime fechaRegistro;

    public Proveedor() {}

    // Getters y Setters
    public int getId()                          { return id; }
    public void setId(int id)                   { this.id = id; }

    public int getTipoDocumentoId()             { return tipoDocumentoId; }
    public void setTipoDocumentoId(int v)       { this.tipoDocumentoId = v; }

    public String getTipoDocumentoNombre()      { return tipoDocumentoNombre; }
    public void setTipoDocumentoNombre(String v){ this.tipoDocumentoNombre = v; }

    public String getNumeroDocumento()          { return numeroDocumento; }
    public void setNumeroDocumento(String v)    { this.numeroDocumento = v; }

    public String getRazonSocial()              { return razonSocial; }
    public void setRazonSocial(String v)        { this.razonSocial = v; }

    public String getNombreContacto()           { return nombreContacto; }
    public void setNombreContacto(String v)     { this.nombreContacto = v; }

    public String getCargoContacto()            { return cargoContacto; }
    public void setCargoContacto(String v)      { this.cargoContacto = v; }

    public String getEmail()                    { return email; }
    public void setEmail(String v)              { this.email = v; }

    public String getTelefono()                 { return telefono; }
    public void setTelefono(String v)           { this.telefono = v; }

    public String getDireccion()                { return direccion; }
    public void setDireccion(String v)          { this.direccion = v; }

    public String getPais()                     { return pais; }
    public void setPais(String v)               { this.pais = v; }

    public String getDepartamento()             { return departamento; }
    public void setDepartamento(String v)       { this.departamento = v; }

    public String getCiudad()                   { return ciudad; }
    public void setCiudad(String v)             { this.ciudad = v; }

    public String getTipoProveedor()            { return tipoProveedor; }
    public void setTipoProveedor(String v)      { this.tipoProveedor = v; }

    public String getEstado()                   { return estado; }
    public void setEstado(String v)             { this.estado = v; }

    public String getObservaciones()            { return observaciones; }
    public void setObservaciones(String v)      { this.observaciones = v; }

    public int getCreadoPorId()                 { return creadoPorId; }
    public void setCreadoPorId(int v)           { this.creadoPorId = v; }

    public String getCreadoPorNombre()          { return creadoPorNombre; }
    public void setCreadoPorNombre(String v)    { this.creadoPorNombre = v; }

    public LocalDateTime getFechaRegistro()     { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime v){ this.fechaRegistro = v; }
}
