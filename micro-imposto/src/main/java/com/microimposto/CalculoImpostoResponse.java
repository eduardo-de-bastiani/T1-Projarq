package com.microimposto;

public class CalculoImpostoResponse {
    private double valorImposto;
    private String localidade;
    private String detalhes;

    public CalculoImpostoResponse() {}

    public CalculoImpostoResponse(double valorImposto, String localidade, String detalhes) {
        this.valorImposto = valorImposto;
        this.localidade = localidade;
        this.detalhes = detalhes;
    }

    // Getters e Setters
    public double getValorImposto() { return valorImposto; }
    public void setValorImposto(double valorImposto) { this.valorImposto = valorImposto; }

    public String getLocalidade() { return localidade; }
    public void setLocalidade(String localidade) { this.localidade = localidade; }

    public String getDetalhes() { return detalhes; }
    public void setDetalhes(String detalhes) { this.detalhes = detalhes; }
}

