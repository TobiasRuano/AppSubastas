package com.trotos.appsubastas.Modelos;

import java.io.Serializable;

public class Bid implements Serializable {

    private int catalogItemId;
    private int amount;
    private int userId;

    public Bid(int catalogItemId, int amount, int userId) {
        this.catalogItemId = catalogItemId;
        this.amount = amount;
        this.userId = userId;
    }

    public int getCatalogItemId() {
        return catalogItemId;
    }

    public void setCatalogItemId(int catalogItemId) {
        this.catalogItemId = catalogItemId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
