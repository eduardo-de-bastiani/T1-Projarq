package com.bcopstein.sistvendas.dominio.servicos.DecoratorImposto;

import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.servicos.StrategyImposto.ICalculoImposto;

public abstract class ImpostoDecorator implements ICalculoImposto {
    protected ICalculoImposto outroImposto;

    public ImpostoDecorator(ICalculoImposto outroImposto) {
        this.outroImposto = outroImposto;
    }

    protected double calculaOutroImposto(OrcamentoModel orcamento) {
        return (outroImposto == null) ? 0 : outroImposto.calculoimposto(orcamento);
    }
}
