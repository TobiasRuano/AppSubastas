package com.trotos.appsubastas;

import java.io.Serializable;

public class ItemCatalogo extends Producto implements Serializable {

    private String estado;
    private String precioBase;
    private String valorActual;
    private String color;

    public ItemCatalogo(String id, String estado, String descripcion, String precioBase, String valorActual, String color, String descripcionCompleta, String descripcionBreve) {
        super(id, descripcion, descripcionCompleta, descripcionBreve);
        this.estado = estado;
        this.precioBase = precioBase;
        this.valorActual = valorActual;
        this.color = color;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
}
