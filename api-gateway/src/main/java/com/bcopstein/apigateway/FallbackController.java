package com.bcopstein.apigateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {
    @GetMapping("/sistema-vendas")
    public ResponseEntity<String> sistemaVendasFallback() {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Serviços temporariamente indisponíveis. Tente novamente.");
            //Implementação mais adequada seria retornar dados em cache de
            //uma requisição anterior com sucesso
    }
}
