package com.trotos.appsubastas.Modelos;

public class Bid {

    public int getCatalogItemid() {
        return catalogItemid;
    }

    public void setCatalogItemid(int catalogItemid) {
        this.catalogItemid = catalogItemid;
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

    private int catalogItemid;
    private int amount;
    private int userId;

    public Bid(int catalogItemid, int amount, int userId) {
        this.catalogItemid = catalogItemid;
        this.amount = amount;
        this.userId = userId;
    }
}
