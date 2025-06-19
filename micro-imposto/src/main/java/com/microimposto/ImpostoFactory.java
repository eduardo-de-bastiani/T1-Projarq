package com.microimposto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImpostoFactory {
    
    @Autowired
    private ImpostoFederal impostoFederal;
    
    @Autowired
    private ImpostoEstadual impostoEstadual;
    
    @Autowired
    private ImpostoMunicipal impostoMunicipal;

    public ICalculoImposto criar(String localidade) {
        switch (localidade.toLowerCase()) {
            case "rs":
            case "rio grande do sul":
                // RS: Federal + Estadual + Municipal
                return new ImpostoComposto(
                    impostoFederal,
                    new ImpostoComposto(impostoEstadual, impostoMunicipal)
                );
            case "sp":
            case "sao paulo":
                // SP: Federal + Estadual (sem municipal para simplificar)
                return new ImpostoComposto(impostoFederal, impostoEstadual);
            case "rj":
            case "rio de janeiro":
                // RJ: Federal + Municipal (sem estadual para simplificar)
                return new ImpostoComposto(impostoFederal, impostoMunicipal);
            default:
                // Padrão: apenas federal
                return impostoFederal;
        }
    }
}

