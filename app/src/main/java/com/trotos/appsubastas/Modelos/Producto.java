package com.trotos.appsubastas.Modelos;

import java.io.Serializable;

public class Producto implements Serializable {

    private String id;
    private String descripcion;
    private String descripcionCompleta;
    private String descripcionBreve;

    public Producto(String id, String descripcion, String descripcionCompleta, String descripcionBreve) {
        this.id = id;
        this.descripcion = descripcion;
        this.descripcionCompleta = descripcionCompleta;
        this.descripcionBreve = descripcionBreve;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
