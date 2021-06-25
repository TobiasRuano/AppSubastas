package com.trotos.appsubastas.Modelos;

public class Bid {

    private int catalogItemid;
    private int amount;
    private int userId;

    public Bid(int catalogItemid, int amount, int userId) {
        this.catalogItemid = catalogItemid;
        this.amount = amount;
        this.userId = userId;
    }
}
