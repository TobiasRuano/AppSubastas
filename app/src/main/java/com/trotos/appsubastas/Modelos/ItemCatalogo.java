package com.trotos.appsubastas.Modelos;

import java.io.Serializable;

public class ItemCatalogo extends Producto implements Serializable {

    private String estado;
    private int precioBase;
    private int valorActual;
    private String color;
    private String moneda;
    private int subastaid;
    private int itemId;
    private String comision;
    private String precioEnvio;
    private String ganador;

    public ItemCatalogo(String id, String estado, String descripcion, int precioBase, int valorActual, String color, String descripcionCompleta, String descripcionBreve, String moneda, int subastaid) {
        super(id, descripcion, descripcionCompleta, descripcionBreve);
        this.estado = estado;
        this.precioBase = precioBase;
        this.valorActual = valorActual;
        this.color = color;
        this.moneda = moneda;
        this.subastaid = subastaid;
    }

    public ItemCatalogo(String id, String descripcion, String descripcionCompleta, String descripcionBreve, String estado, int precioBase, int valorActual, String color, String moneda, int subastaid, int itemId, String comision, String precioEnvio, String ganador) {
        super(id, descripcion, descripcionCompleta, descripcionBreve);
        this.estado = estado;
        this.precioBase = precioBase;
        this.valorActual = valorActual;
        this.color = color;
        this.moneda = moneda;
        this.subastaid = subastaid;
        this.itemId = itemId;
        this.comision = comision;
        this.precioEnvio = precioEnvio;
        this.ganador = ganador;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getComision() {
        return comision;
    }

    public void setComision(String comision) {
        this.comision = comision;
    }

    public String getPrecioEnvio() {
        return precioEnvio;
    }

    public void setPrecioEnvio(String precioEnvio) {
        this.precioEnvio = precioEnvio;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(int precioBase) {
        this.precioBase = precioBase;
    }

    public int getValorActual() {
        return valorActual;
    }

    public void setValorActual(int valorActual) {
        this.valorActual = valorActual;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getMoneda() {
        return moneda;
    }

    public int getSubastaid() { return subastaid; }

    public void setSubastaid(int subastaid) { this.subastaid = subastaid; }
}
