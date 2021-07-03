package com.trotos.appsubastas.Modelos;

public class ResponseItemsPropuestos {

    private String message;
    private ItemCatalogo data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ItemCatalogo getData() {
        return data;
    }

    public void setData(ItemCatalogo data) {
        this.data = data;
    }
}
