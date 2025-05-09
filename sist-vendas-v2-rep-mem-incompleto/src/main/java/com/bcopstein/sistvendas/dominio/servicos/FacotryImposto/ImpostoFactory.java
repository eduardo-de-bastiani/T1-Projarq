package com.bcopstein.sistvendas.dominio.servicos.FacotryImposto;

import org.springframework.stereotype.Component;

import com.bcopstein.sistvendas.auxiliares.Localidade;
import com.bcopstein.sistvendas.dominio.servicos.DecoratorImposto.ImpostoComFederal;
import com.bcopstein.sistvendas.dominio.servicos.StrategyImposto.ICalculoImposto;
import com.bcopstein.sistvendas.dominio.servicos.StrategyImposto.ImpostoFederal;
import com.bcopstein.sistvendas.dominio.servicos.StrategyImposto.ImpostoPB;
import com.bcopstein.sistvendas.dominio.servicos.StrategyImposto.ImpostoRS;
import com.bcopstein.sistvendas.dominio.servicos.StrategyImposto.ImpostoSP;

@Component
public class ImpostoFactory {
    public ICalculoImposto criar(Localidade local) {
        ICalculoImposto impostoRegional = 
        switch (local) {
            case SP -> new ImpostoSP();
            case RS -> new ImpostoRS();
            case PB -> new ImpostoPB();
            default -> throw new IllegalArgumentException("Localidade não suportada");
        };

        ICalculoImposto impostoFederal = new ImpostoFederal();
        return new ImpostoComFederal(impostoRegional, impostoFederal);
    }
}