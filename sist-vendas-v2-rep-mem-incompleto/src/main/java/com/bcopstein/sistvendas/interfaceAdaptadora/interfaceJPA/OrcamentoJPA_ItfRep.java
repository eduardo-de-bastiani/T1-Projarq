package com.bcopstein.sistvendas.interfaceAdaptadora.interfaceJPA;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bcopstein.sistvendas.interfaceAdaptadora.entidades.Orcamento;

public interface OrcamentoJPA_ItfRep extends CrudRepository<Orcamento,Long> {
    List<Orcamento> findAll();

    Orcamento findById(long id);

    List<Orcamento> findByEfetivadoTrueAndDataBetween(Date dataInicial, Date dataFinal);
}
