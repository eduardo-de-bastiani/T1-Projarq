package com.microimposto;

import org.springframework.stereotype.Component;

@Component
public class ImpostoEstadual implements ICalculoImposto {
    
    @Override
    public double calculoImposto(OrcamentoModel orcamento) {
        // ICMS - 18% sobre o subtotal
        return orcamento.getSubtotal() * 0.18;
    }

    @Override
    public String getDescricao() {
        return "ICMS - Imposto sobre Circulação de Mercadorias e Serviços (18%)";
    }
}

