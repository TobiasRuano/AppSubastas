package com.trotos.appsubastas.Modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMPTarjetas {
    @SerializedName("results")
    @Expose
    private List<MPTarjeta> results = null;

    public List<MPTarjeta> getResults() {
        return results;
    }

    public void setResults(List<MPTarjeta> results) {
        this.results = results;
    }
}
