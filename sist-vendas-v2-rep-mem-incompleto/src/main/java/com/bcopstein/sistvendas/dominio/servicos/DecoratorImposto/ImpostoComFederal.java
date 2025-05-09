package com.bcopstein.sistvendas.dominio.servicos.DecoratorImposto;

import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.servicos.StrategyImposto.ICalculoImposto;

public class ImpostoComFederal extends ImpostoDecorator {
    private final ICalculoImposto impostoFederal;

    public ImpostoComFederal(ICalculoImposto base, ICalculoImposto federal) {
        super(base);
        this.impostoFederal = federal;
    }

    @Override
    public double calculoimposto(OrcamentoModel orcamento) {
        return calculaOutroImposto(orcamento) + impostoFederal.calculoimposto(orcamento);
    }
}
