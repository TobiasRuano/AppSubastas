package com.trotos.appsubastas.Modelos;

public class ResponseStatisticsUser {

    private int ganados;
    private int participados;
    private String categoria;

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getGanados() {
        return ganados;
    }

    public void setGanados(int ganados) {
        this.ganados = ganados;
    }

    public int getParticipados() {
        return participados;
    }

    public void setParticipados(int participados) {
        this.participados = participados;
    }

}