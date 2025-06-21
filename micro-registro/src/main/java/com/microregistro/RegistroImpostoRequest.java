package com.microregistro;

public class RegistroImpostoRequest {
    private Long orcamentoId;
    private Double valorVendido;
    private Double valorImposto;
    private String localidade;
    private String dataEfetivacao;
    private String detalhesImposto;

    public RegistroImpostoRequest() {}

    public RegistroImpostoRequest(Long orcamentoId, Double valorVendido, Double valorImposto, 
                                 String localidade, String dataEfetivacao, String detalhesImposto) {
        this.orcamentoId = orcamentoId;
        this.valorVendido = valorVendido;
        this.valorImposto = valorImposto;
        this.localidade = localidade;
        this.dataEfetivacao = dataEfetivacao;
        this.detalhesImposto = detalhesImposto;
    }

    // Getters e Setters
    public Long getOrcamentoId() {
        return orcamentoId;
    }

    public void setOrcamentoId(Long orcamentoId) {
        this.orcamentoId = orcamentoId;
    }

    public Double getValorVendido() {
        return valorVendido;
    }

    public void setValorVendido(Double valorVendido) {
        this.valorVendido = valorVendido;
    }

    public Double getValorImposto() {
        return valorImposto;
    }

    public void setValorImposto(Double valorImposto) {
        this.valorImposto = valorImposto;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getDataEfetivacao() {
        return dataEfetivacao;
    }

    public void setDataEfetivacao(String dataEfetivacao) {
        this.dataEfetivacao = dataEfetivacao;
    }

    public String getDetalhesImposto() {
        return detalhesImposto;
    }

    public void setDetalhesImposto(String detalhesImposto) {
        this.detalhesImposto = detalhesImposto;
    }
}

