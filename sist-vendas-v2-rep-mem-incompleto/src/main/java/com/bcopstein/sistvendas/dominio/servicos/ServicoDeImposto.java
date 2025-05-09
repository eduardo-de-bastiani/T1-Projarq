package com.bcopstein.sistvendas.dominio.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcopstein.sistvendas.auxiliares.Localidade;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.servicos.FacotryImposto.ImpostoFactory;
import com.bcopstein.sistvendas.dominio.servicos.StrategyImposto.ICalculoImposto;

@Service
public class ServicoDeImposto {
    private final ImpostoFactory impostoFactory;

    @Autowired
    public ServicoDeImposto(ImpostoFactory impostoFactory) {
        this.impostoFactory = impostoFactory;
    }

    public double calculaImposto(OrcamentoModel orcamento, Localidade local) {
        ICalculoImposto impostoComposto = impostoFactory.criar(local);
        return impostoComposto.calculoimposto(orcamento);
    }
}
