package com.bcopstein.sistvendas.aplicacao.casosDeUso;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.aplicacao.dtos.OrcamentoDTO;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.servicos.ServicoDeVendas;

@Component
public class OrcamentosEfetivadosUC {
    private ServicoDeVendas servicoDeVendas;

    @Autowired
    public OrcamentosEfetivadosUC(ServicoDeVendas servicoDeVendas){
        this.servicoDeVendas = servicoDeVendas;
    }

    public List<OrcamentoDTO> run(String dataInicio, String dataFim){ 
        List<OrcamentoModel> orcamentos = servicoDeVendas.orcamentosDatas(dataInicio, dataFim);
        return OrcamentoDTO.fromModel(orcamentos);
    }
}
