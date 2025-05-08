package com.bcopstein.sistvendas.dominio.servicos.StrategyImposto;

import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;

public class ImpostoRS implements ICalculoImposto {

    @Override
    public double calculoimposto(OrcamentoModel orcamento) {
        if(orcamento.getCustoItens() < 100){
            return 0;
        }
        return (orcamento.getCustoItens() - 100) * 0.1;
    }
    
}
