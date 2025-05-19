package com.bcopstein.sistvendas.interfaceAdaptadora.implemRepositorios;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.bcopstein.sistvendas.auxiliares.DefaultException;
import com.bcopstein.sistvendas.dominio.modelos.OrcamentoModel;
import com.bcopstein.sistvendas.dominio.persistencia.IOrcamentoRepositorio;
import com.bcopstein.sistvendas.interfaceAdaptadora.entidades.Orcamento;
import com.bcopstein.sistvendas.interfaceAdaptadora.interfaceJPA.OrcamentoJPA_ItfRep;

@Repository
@Primary
public class OrcamentoRepJPA implements IOrcamentoRepositorio {
    private OrcamentoJPA_ItfRep orcamentoRepository;

    @Autowired
    public OrcamentoRepJPA(OrcamentoJPA_ItfRep orcamentoRepository){
        this.orcamentoRepository = orcamentoRepository;
    }

    @Override
    public List<OrcamentoModel> todos() {
        List<Orcamento> orcamentos = orcamentoRepository.findAll();
        return orcamentos.stream()
                .map(orcamento -> orcamento.toModel())
                .toList();
    }

    @Override
    public OrcamentoModel cadastra(OrcamentoModel orcamento) {
        Orcamento entidade = Orcamento.fromModel(orcamento);
        Orcamento salvo = orcamentoRepository.save(entidade);
        return salvo.toModel();
    }

    @Override
    public OrcamentoModel recuperaPorId(long id) {
        Orcamento orcamento = orcamentoRepository.findById(id).orElse(null);

        if (orcamento == null) {
            throw new DefaultException("Orçamento inexistente");
        }

        return orcamento.toModel();
    }

    @Override
    public void marcaComoEfetivado(long id) {
        Orcamento orcamento = orcamentoRepository.findById(id).orElse(null);

        if (orcamento == null) {
            throw new DefaultException("Orçamento inexistente");
        }

        orcamento.efetiva();
        orcamentoRepository.save(orcamento);
    }

    @Override
    public List<OrcamentoModel> recuperaData(String dataInicial, String dataFinal) {
        LocalDate dataI = LocalDate.parse(dataInicial);
        LocalDate dataF = LocalDate.parse(dataFinal).plusDays(1); 

        List<Orcamento> orcamentos = orcamentoRepository.findByEfetivadoTrueAndDataBetween(
            Date.valueOf(dataI),
            Date.valueOf(dataF)
        );

        return orcamentos.stream()
            .map(orcamento -> orcamento.toModel())
            .toList();
    }
}
