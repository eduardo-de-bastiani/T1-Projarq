package com.bcopstein.sistvendas.interfaceAdaptadora.implemRepositorios;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

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
        Orcamento orcamentoEntidade = new Orcamento();
        orcamentoRepository.save(orcamentoEntidade);
        return orcamentoEntidade.toModel();
    }

    @Override
    public OrcamentoModel recuperaPorId(long id) {
        Orcamento orcamento = orcamentoRepository.findById(id);
        return orcamento.toModel();
    }

    @Override
    public void marcaComoEfetivado(long id) {
        Orcamento orcamento = orcamentoRepository.findById(id);
        orcamento.efetiva();
        orcamentoRepository.save(orcamento);
    }

    @Override
    public List<OrcamentoModel> recuperaData(String dataInicial, String dataFinal) {
        Date dataI = Date.valueOf(dataInicial);
        Date dataF = Date.valueOf(dataFinal);

        List<Orcamento> orcamentos = orcamentoRepository.findByEfetivadoTrueAndDataBetween(dataI, dataF);
        return orcamentos.stream()
                .map(orcamento -> orcamento.toModel())
                .toList();
    }
    
}
