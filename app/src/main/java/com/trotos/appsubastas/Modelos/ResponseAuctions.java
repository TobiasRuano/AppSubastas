package com.trotos.appsubastas.Modelos;

import java.util.List;

public class ResponseAuctions {

    private List<Auction> data = null;

    public List<Auction> getData() {
        return data;
    }

    public void setData(List<Auction> data) {
        this.data = data;
    }
}
