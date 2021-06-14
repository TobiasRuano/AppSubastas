package com.trotos.appsubastas.Modelos;

import java.io.Serializable;
import java.util.Date;

public class MPTarjeta implements Serializable {

    private int idUsuario;
    private String nombreTarjeta;
    private String proveedorTarjeta;
    private String numeroTarjeta;
    private Date fechaExpTarjeta;
    private int cvcTarjeta;

    public MPTarjeta(int idUsuario, String nombre, String numero, String proveedor, int cvc, Date fecha) {
        this.idUsuario = idUsuario;
        this.nombreTarjeta = nombre;
        this.numeroTarjeta = numero;
        this.proveedorTarjeta = proveedor;
        this.cvcTarjeta = cvc;
        this.fechaExpTarjeta = fecha;
    }

    public int getIdUsuario() { return idUsuario; }

    public String getNombreTarjeta() {
        return nombreTarjeta;
    }

    public String getProveedorTarjeta() {
        return proveedorTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public int getCvcTarjeta() { return this.cvcTarjeta; }

    public Date getFechaExpTarjeta() {
        return fechaExpTarjeta;
    }

    public void setNombreTarjeta(String nombreTarjeta) { this.nombreTarjeta = nombreTarjeta; }

    public void setProveedorTarjeta(String proveedorTarjeta) { this.proveedorTarjeta = proveedorTarjeta; }

    public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }

    public void setFechaExpTarjeta(Date fechaExpTarjeta) { this.fechaExpTarjeta = fechaExpTarjeta; }

    public void setCvcTarjeta(int cvcTarjeta) { this.cvcTarjeta = cvcTarjeta; }

    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
}
