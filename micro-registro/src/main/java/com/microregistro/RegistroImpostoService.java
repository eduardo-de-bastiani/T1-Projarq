package com.microregistro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RegistroImpostoService {

    @Autowired
    private RegistroImpostoRepository repository;

    public RegistroImposto registrarImposto(RegistroImpostoRequest request) {
        LocalDateTime dataEfetivacao;
        try {
            dataEfetivacao = LocalDateTime.parse(request.getDataEfetivacao());
        } catch (Exception e) {
            dataEfetivacao = LocalDateTime.now();
        }

        RegistroImposto registro = new RegistroImposto(
            request.getOrcamentoId(),
            request.getValorVendido(),
            request.getValorImposto(),
            request.getLocalidade(),
            dataEfetivacao,
            request.getDetalhesImposto()
        );

        return repository.save(registro);
    }

    public RelatorioVendasResponse gerarRelatorio(int mes, int ano) {
        Double totalVendido = repository.sumValorVendidoByAnoAndMes(ano, mes);
        Double totalImpostos = repository.sumValorImpostoByAnoAndMes(ano, mes);

        // Se não há dados, retorna zeros
        if (totalVendido == null) totalVendido = 0.0;
        if (totalImpostos == null) totalImpostos = 0.0;

        return new RelatorioVendasResponse(mes, ano, totalVendido, totalImpostos);
    }

    public List<RegistroImposto> buscarTodosRegistros() {
        return repository.findAll();
    }
}

