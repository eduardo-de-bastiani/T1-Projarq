package com.microregistro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/registro")
@CrossOrigin(origins = "*")
public class RegistroImpostoController {

    @Autowired
    private RegistroImpostoService service;

    @PostMapping("/impostos")
    public ResponseEntity<RegistroImposto> registrarImposto(@RequestBody RegistroImpostoRequest request) {
        try {
            RegistroImposto registro = service.registrarImposto(request);
            return ResponseEntity.ok(registro);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/relatorio/{ano}/{mes}")
    public ResponseEntity<RelatorioMensalResponse> gerarRelatorioMensal(
            @PathVariable int ano, 
            @PathVariable int mes) {
        try {
            RelatorioMensalResponse relatorio = service.gerarRelatorioMensal(mes, ano);
            return ResponseEntity.ok(relatorio);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/impostos")
    public ResponseEntity<List<RegistroImposto>> listarTodosRegistros() {
        try {
            List<RegistroImposto> registros = service.buscarTodosRegistros();
            return ResponseEntity.ok(registros);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/impostos/periodo")
    public ResponseEntity<List<RegistroImposto>> buscarPorPeriodo(
            @RequestParam String inicio,
            @RequestParam String fim) {
        try {
            LocalDateTime dataInicio = LocalDateTime.parse(inicio);
            LocalDateTime dataFim = LocalDateTime.parse(fim);
            List<RegistroImposto> registros = service.buscarRegistrosPorPeriodo(dataInicio, dataFim);
            return ResponseEntity.ok(registros);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Microserviço de Registro está funcionando!");
    }
}

