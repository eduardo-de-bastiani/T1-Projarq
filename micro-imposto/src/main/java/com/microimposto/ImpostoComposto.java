package com.microimposto;

import org.springframework.stereotype.Component;

@Component
public class ImpostoComposto implements ICalculoImposto {
    private final ICalculoImposto impostoBase;
    private final ICalculoImposto impostoAdicional;

    public ImpostoComposto(ICalculoImposto impostoBase, ICalculoImposto impostoAdicional) {
        this.impostoBase = impostoBase;
        this.impostoAdicional = impostoAdicional;
    }

    @Override
    public double calculoImposto(OrcamentoModel orcamento) {
        return impostoBase.calculoImposto(orcamento) + impostoAdicional.calculoImposto(orcamento);
    }

    @Override
    public String getDescricao() {
        return impostoBase.getDescricao() + " + " + impostoAdicional.getDescricao();
    }
}

