package com.trotos.appsubastas.Modelos;

import java.io.Serializable;

public class Subasta implements Serializable {
    private String color;
    private String name;
    private String state;
    private String category;
    private int id;
    //private List<ItemCatalogo> catalogo;

    public Subasta(String color, String name, String state, String category, int id) {
        this.color = color;
        this.name = name;
        this.state = state;
        this.category = category;
        this.id = id;
        //this.catalogo = catalogo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
