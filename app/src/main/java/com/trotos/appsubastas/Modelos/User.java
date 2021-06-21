package com.trotos.appsubastas.Modelos;

import java.io.Serializable;

public class User implements Serializable {

    int id;
    String name;
    String surname;
    String mail;
    int dni;
    String address;
    String category;

    public User(int id, String name, String surname, String mail, int dni, String address, String category) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.dni = dni;
        this.address = address;
        this.category = category;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public String getMail() { return mail; }

    public void setMail(String mail) { this.mail = mail; }

    public int getDni() { return dni; }

    public void setDni(int dni) { this.dni = dni; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
