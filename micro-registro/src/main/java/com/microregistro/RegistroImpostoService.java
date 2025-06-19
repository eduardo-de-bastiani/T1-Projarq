package com.microregistro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistroImpostoService {
    
    @Autowired
    private RegistroImpostoRepository repository;

    public RegistroImposto registrarImposto(RegistroImpostoRequest request) {
        RegistroImposto registro = new RegistroImposto(
            request.getOrcamentoId(),
            request.getValorVendido(),
            request.getValorImposto(),
            request.getLocalidade(),
            request.getDataEfetivacao() != null ? request.getDataEfetivacao() : LocalDateTime.now(),
            request.getDetalhesImposto()
        );
        
        return repository.save(registro);
    }

    public RelatorioMensalResponse gerarRelatorioMensal(int mes, int ano) {
        Double totalVendido = repository.sumValorVendidoByMesAno(mes, ano);
        Double totalImpostos = repository.sumValorImpostoByMesAno(mes, ano);
        Long quantidadeOrcamentos = repository.countByMesAno(mes, ano);
        
        return new RelatorioMensalResponse(mes, ano, totalVendido, totalImpostos, quantidadeOrcamentos);
    }

    public List<RegistroImposto> buscarRegistrosPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return repository.findByDataEfetivacaoBetween(inicio, fim);
    }

    public List<RegistroImposto> buscarTodosRegistros() {
        return repository.findAll();
    }
}

