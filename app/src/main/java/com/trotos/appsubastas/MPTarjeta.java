package com.trotos.appsubastas;

import java.util.Date;

public class MPTarjeta {
    private String nombreTarjeta;
    private String proveedorTarjeta;
    private String numeroTarjeta;
    private Date fechaExpTarjeta;

    public MPTarjeta(String nombre, String numero, String proveedor, Date fecha) {
        this.nombreTarjeta = nombre;
        this.numeroTarjeta = numero;
        this.proveedorTarjeta = proveedor;
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

    public Date getFechaExpTarjeta() {
        return fechaExpTarjeta;
    }
}
