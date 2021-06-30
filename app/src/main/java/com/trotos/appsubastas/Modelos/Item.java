package com.trotos.appsubastas.Modelos;

import java.io.Serializable;

public class Item implements Serializable {

    private int id;
    private String title;
    private String description;
    private String urlImage;
    private int commission;
    private int basePrice;
    private String currency;
    private String status;

    public Item(int id, String title, String description, String urlImage, int commission, int basePrice, String status, String currency) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.urlImage = urlImage;
        this.commission = commission;
        this.basePrice = basePrice;
        this.status = status;
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
