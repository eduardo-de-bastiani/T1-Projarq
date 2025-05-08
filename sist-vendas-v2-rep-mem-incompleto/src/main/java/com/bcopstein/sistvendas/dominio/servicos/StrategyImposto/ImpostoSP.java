package com.bcopstein.sistvendas.dominio.servicos.StrategyImposto;

import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;

public class ImpostoSP implements ICalculoImposto {

    @Override
    public double calculoimposto(OrcamentoModel orcamento) {
        return orcamento.getCustoItens() * 0.12;
    }
    
}
