package com.trotos.appsubastas.Modelos;

public class ResponseCreateMP {

    private String message;
    private MPTarjeta data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MPTarjeta getData() {
        return data;
    }

    public void setData(MPTarjeta data) {
        this.data = data;
    }
}
