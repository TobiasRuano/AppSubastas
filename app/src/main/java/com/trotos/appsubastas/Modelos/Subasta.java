package com.trotos.appsubastas.Modelos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Subasta implements Serializable {
    private String color;
    private String name;
    private String status;
    private String category;
    private int id;
    private Date startTime;
    private Date endTime;
    private String currency;

    private List<ItemCatalogo> catalogos;

    public Subasta(String color, String name, String status, String category, int id, Date startTime, Date endTime, String currency, List<ItemCatalogo> catalogos) {
        this.color = color;
        this.name = name;
        this.status = status;
        this.category = category;
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currency = currency;
        this.catalogos = catalogos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<ItemCatalogo> getCatalogos() {
        return catalogos;
    }

    public void setCatalogos(List<ItemCatalogo> catalogos) {
        this.catalogos = catalogos;
    }
}
