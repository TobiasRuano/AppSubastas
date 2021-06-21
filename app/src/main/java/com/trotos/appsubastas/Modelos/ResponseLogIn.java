package com.trotos.appsubastas.Modelos;

public class ResponseLogIn {

    private String message;
    private String token;
    private User data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return data;
    }

    public void setUser(User data) {
        this.data = data;
    }
}
