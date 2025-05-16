package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.aplicacao.dtos.OrcamentoDTO;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.servicos.ServicoDeVendas;

@Component
public class EfetivaOrcamentoUC {
    private ServicoDeVendas servicoDeVendas;
    
    @Autowired
    public EfetivaOrcamentoUC(ServicoDeVendas servicoDeVendas){
        this.servicoDeVendas = servicoDeVendas;
    }

    public OrcamentoDTO run(long idOrcamento){
        System.out.println("idOrcamento = " + idOrcamento);
        OrcamentoModel orcamentoEfetivado = servicoDeVendas.efetivaOrcamento(idOrcamento);
        if (orcamentoEfetivado == null) {
            throw new IllegalArgumentException("Houve um erro ao efetivar o orçamento.");
        }
        return OrcamentoDTO.fromModel(orcamentoEfetivado);
    }
}