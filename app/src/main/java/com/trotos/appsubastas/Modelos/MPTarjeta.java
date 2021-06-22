package com.trotos.appsubastas.Modelos;

import java.io.Serializable;
import java.util.Date;

public class MPTarjeta implements Serializable {

    private int id;
    private int userId;
    private String cardHolderName;
    private Boolean isDefaultCard;
    private String cardNumber;
    private Date expiration;
    private int code;

    public MPTarjeta(int id, int userId, String cardHolderName, Boolean isDefaultCard, String cardNumber, int code, Date expiration) {
        this.id = id;
        this.userId = userId;
        this.cardHolderName = cardHolderName;
        this.isDefaultCard = isDefaultCard;
        this.cardNumber = cardNumber;
        this.code = code;
        this.expiration = expiration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public Boolean getDefaultCard() {
        return isDefaultCard;
    }

    public void setDefaultCard(Boolean defaultCard) {
        isDefaultCard = defaultCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
