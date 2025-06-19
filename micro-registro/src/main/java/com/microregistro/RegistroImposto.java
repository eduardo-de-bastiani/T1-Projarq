package com.microregistro;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registro_impostos")
public class RegistroImposto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "orcamento_id", nullable = false)
    private Long orcamentoId;
    
    @Column(name = "valor_vendido", nullable = false)
    private Double valorVendido;
    
    @Column(name = "valor_imposto", nullable = false)
    private Double valorImposto;
    
    @Column(name = "localidade", nullable = false)
    private String localidade;
    
    @Column(name = "data_efetivacao", nullable = false)
    private LocalDateTime dataEfetivacao;
    
    @Column(name = "detalhes_imposto")
    private String detalhesImposto;

    // Construtores
    public RegistroImposto() {}

    public RegistroImposto(Long orcamentoId, Double valorVendido, Double valorImposto, 
                          String localidade, LocalDateTime dataEfetivacao, String detalhesImposto) {
        this.orcamentoId = orcamentoId;
        this.valorVendido = valorVendido;
        this.valorImposto = valorImposto;
        this.localidade = localidade;
        this.dataEfetivacao = dataEfetivacao;
        this.detalhesImposto = detalhesImposto;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOrcamentoId() { return orcamentoId; }
    public void setOrcamentoId(Long orcamentoId) { this.orcamentoId = orcamentoId; }

    public Double getValorVendido() { return valorVendido; }
    public void setValorVendido(Double valorVendido) { this.valorVendido = valorVendido; }

    public Double getValorImposto() { return valorImposto; }
    public void setValorImposto(Double valorImposto) { this.valorImposto = valorImposto; }

    public String getLocalidade() { return localidade; }
    public void setLocalidade(String localidade) { this.localidade = localidade; }

    public LocalDateTime getDataEfetivacao() { return dataEfetivacao; }
    public void setDataEfetivacao(LocalDateTime dataEfetivacao) { this.dataEfetivacao = dataEfetivacao; }

    public String getDetalhesImposto() { return detalhesImposto; }
    public void setDetalhesImposto(String detalhesImposto) { this.detalhesImposto = detalhesImposto; }
}

