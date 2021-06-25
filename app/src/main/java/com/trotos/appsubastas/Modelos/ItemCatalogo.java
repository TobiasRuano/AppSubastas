package com.trotos.appsubastas.Modelos;

import java.io.Serializable;
import java.util.Date;

public class ItemCatalogo extends Item implements Serializable {

    private int shippingPrice;
    private int auctionId;
    private int itemId;
    private int winnerId;
    private Date startTime;
    private Date endTime;

    public ItemCatalogo(int id, String title, String description, String urlImage, int commission, int basePrice, String status, int commission1, int basePrice1, int shippingPrice, int auctionId, int itemId, int winnerId, Date startTime, Date endTime) {
        super(id, title, description, urlImage, commission, basePrice, status);
        this.shippingPrice = shippingPrice;
        this.auctionId = auctionId;
        this.itemId = itemId;
        this.winnerId = winnerId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(int shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(int auctionId) {
        this.auctionId = auctionId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
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
}