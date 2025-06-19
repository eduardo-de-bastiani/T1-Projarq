package com.microimposto;

import org.springframework.stereotype.Component;

@Component
public class ImpostoFederal implements ICalculoImposto {
    
    @Override
    public double calculoImposto(OrcamentoModel orcamento) {
        // PIS/COFINS - 9.25% sobre o subtotal
        return orcamento.getSubtotal() * 0.0925;
    }

    @Override
    public String getDescricao() {
        return "PIS/COFINS - Contribuições Federais (9.25%)";
    }
}

