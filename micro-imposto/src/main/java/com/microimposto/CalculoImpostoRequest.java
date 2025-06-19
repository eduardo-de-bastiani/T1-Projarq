package com.microimposto;

public class CalculoImpostoRequest {
    private OrcamentoModel orcamento;
    private String localidade;

    public CalculoImpostoRequest() {}

    public CalculoImpostoRequest(OrcamentoModel orcamento, String localidade) {
        this.orcamento = orcamento;
        this.localidade = localidade;
    }

    // Getters e Setters
    public OrcamentoModel getOrcamento() { return orcamento; }
    public void setOrcamento(OrcamentoModel orcamento) { this.orcamento = orcamento; }

    public String getLocalidade() { return localidade; }
    public void setLocalidade(String localidade) { this.localidade = localidade; }
}

