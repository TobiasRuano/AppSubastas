package com.trotos.appsubastas;

import java.io.Serializable;
import java.util.Date;

public class MPTarjeta implements Serializable {

    private String nombreTarjeta;
    private String proveedorTarjeta;
    private String numeroTarjeta;
    private Date fechaExpTarjeta;
    private String cvcTarjeta;

    public MPTarjeta(String nombre, String numero, String proveedor, String cvc, Date fecha) {
        this.nombreTarjeta = nombre;
        this.numeroTarjeta = numero;
        this.proveedorTarjeta = proveedor;
        this.cvcTarjeta = cvc;
        this.fechaExpTarjeta = fecha;
    }

    public String getNombreTarjeta() {
        return nombreTarjeta;
    }

    public String getProveedorTarjeta() {
        return proveedorTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public String getCvcTarjeta() { return this.cvcTarjeta; }

    public Date getFechaExpTarjeta() {
        return fechaExpTarjeta;
    }

    public void setNombreTarjeta(String nombreTarjeta) { this.nombreTarjeta = nombreTarjeta; }

    public void setProveedorTarjeta(String proveedorTarjeta) { this.proveedorTarjeta = proveedorTarjeta; }

    public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }

    public void setFechaExpTarjeta(Date fechaExpTarjeta) { this.fechaExpTarjeta = fechaExpTarjeta; }

    public void setCvcTarjeta(String cvcTarjeta) { this.cvcTarjeta = cvcTarjeta; }
}
