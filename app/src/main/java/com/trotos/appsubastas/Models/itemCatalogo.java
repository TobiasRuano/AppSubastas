package com.trotos.appsubastas;

import java.io.Serializable;

public class itemCatalogo implements Serializable {

    private String id;
    private String estado;
    private String descripcion;
    private String precioBase;
    private String valorActual;
    private String color;
    private String descripcionCompleta;
    private String descripcionBreve;

    public itemCatalogo(String id, String estado, String descripcion, String precioBase, String valorActual, String color, String descripcionCompleta, String descripcionBreve) {
        this.id = id;
        this.estado = estado;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
        this.valorActual = valorActual;
        this.color = color;
        this.descripcionCompleta = descripcionCompleta;
        this.descripcionBreve = descripcionBreve;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(String precioBase) {
        this.precioBase = precioBase;
    }

    public String getValorActual() {
        return valorActual;
    }

    public void setValorActual(String valorActual) {
        this.valorActual = valorActual;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescripcionCompleta() {
        return descripcionCompleta;
    }

    public void setDescripcionCompleta(String descripcionCompleta) {
        this.descripcionCompleta = descripcionCompleta;
    }

    public String getDescripcionBreve() {
        return descripcionBreve;
    }

    public void setDescripcionBreve(String descripcionBreve) {
        this.descripcionBreve = descripcionBreve;
    }
}
