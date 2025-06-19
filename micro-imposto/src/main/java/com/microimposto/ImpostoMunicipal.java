package com.microimposto;

import org.springframework.stereotype.Component;

@Component
public class ImpostoMunicipal implements ICalculoImposto {
    
    @Override
    public double calculoImposto(OrcamentoModel orcamento) {
        // ISS - 5% sobre o subtotal
        return orcamento.getSubtotal() * 0.05;
    }

    @Override
    public String getDescricao() {
        return "ISS - Imposto sobre Serviços (5%)";
    }
}

