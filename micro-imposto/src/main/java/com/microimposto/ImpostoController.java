package com.microimposto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/impostos")
@CrossOrigin(origins = "*")
public class ImpostoController {

    @Autowired
    private ServicoDeImposto servicoDeImposto;

    @PostMapping("/calcular")
    public ResponseEntity<CalculoImpostoResponse> calcularImposto(@RequestBody CalculoImpostoRequest request) {
        try {
            double valorImposto = servicoDeImposto.calculaImposto(request.getOrcamento(), request.getLocalidade());
            String detalhes = servicoDeImposto.getDetalhesImposto(request.getLocalidade());
            
            CalculoImpostoResponse response = new CalculoImpostoResponse(valorImposto, request.getLocalidade(), detalhes);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Microserviço de Impostos está funcionando!");
    }
}

