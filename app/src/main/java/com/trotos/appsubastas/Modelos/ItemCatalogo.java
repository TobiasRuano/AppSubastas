package com.trotos.appsubastas.Modelos;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ItemCatalogo extends Item implements Serializable {

    private int shippingPrice;
    private int auctionId;
    private int itemId;
    private int winnerId;
    private Date startTime;
    private Date endTime;

    public ItemCatalogo(String currency, int id, String title, String description, String urlImage, int commission, int basePrice, String status, int shippingPrice, int auctionId, int itemId, int winnerId, Date startTime, Date endTime) {
        super(itemId, title, description, urlImage, commission, basePrice, status, currency);
        this.shippingPrice = shippingPrice;
        this.auctionId = auctionId;
        this.itemId = id;
        this.winnerId = winnerId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTimeStatus() {
        Date nowDate = new Date();
        String stateString;
        if(nowDate.after(getStartTime()) && nowDate.before(getEndTime())) {
            long time = getEndTime().getTime() - nowDate.getTime();
            long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
            stateString = "Finaliza en: " + minutes + " minutos";
            this.setStatus("Auctioning");
        } else if (nowDate.before(getStartTime())) {
            String date = parseDate(getStartTime());
            stateString = "Inicio: " + date;
            this.setStatus("Programmed");
        } else {
            stateString = "Finalizada";
            this.setStatus("Ended");
        }
        return stateString;
    }

    private String parseDate(Date date) {
        @SuppressLint("SimpleDateFormat") Format formatter = new SimpleDateFormat("dd-MM-yy HH:mm a");
        return formatter.format(date);
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
