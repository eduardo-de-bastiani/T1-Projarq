package com.microregistro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registro")
@CrossOrigin(origins = "*")
public class RegistroController {

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
    public ResponseEntity<RelatorioVendasResponse> gerarRelatorio(@PathVariable int ano, @PathVariable int mes) {
        try {
            RelatorioVendasResponse relatorio = service.gerarRelatorio(mes, ano);
            return ResponseEntity.ok(relatorio);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Microserviço de Registro está funcionando!");
    }
}

