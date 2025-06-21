package com.microregistro;

public class RelatorioVendasResponse {
    private int mes;
    private int ano;
    private Double totalVendido;
    private Double totalImpostos;

    public RelatorioVendasResponse() {}

    public RelatorioVendasResponse(int mes, int ano, Double totalVendido, Double totalImpostos) {
        this.mes = mes;
        this.ano = ano;
        this.totalVendido = totalVendido;
        this.totalImpostos = totalImpostos;
    }

    // Getters e Setters
    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Double getTotalVendido() {
        return totalVendido;
    }

    public void setTotalVendido(Double totalVendido) {
        this.totalVendido = totalVendido;
    }

    public Double getTotalImpostos() {
        return totalImpostos;
    }

    public void setTotalImpostos(Double totalImpostos) {
        this.totalImpostos = totalImpostos;
    }
}

