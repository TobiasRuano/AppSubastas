package com.trotos.appsubastas.Modelos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Auction implements Serializable {
    private String title;
    private String status;
    private String category;
    private int auctioneerId;
    private int id;
    private Date startTime;
    private Date endTime;
    private String currency;

    private List<ItemCatalogo> catalogos;

    public Auction(String title, String status, String category, int id, Date startTime, Date endTime, String currency, List<ItemCatalogo> catalogos) {
        this.title = title;
        this.status = status;
        this.category = category;
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currency = currency;
        this.catalogos = catalogos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
