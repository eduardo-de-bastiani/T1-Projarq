# Exemplos de Uso dos Microserviços

## 1. Testando o Microserviço de Impostos

### Calcular impostos para RS (todos os impostos)
```bash
curl -X POST http://localhost:8081/api/impostos/calcular \
  -H "Content-Type: application/json" \
  -d '{
    "orcamento": {
      "id": 1,
      "subtotal": 1000.0,
      "imposto": 0.0,
      "total": 0.0,
      "itens": [
        {
          "codigoProduto": "PROD001",
          "quantidade": 2,
          "precoUnitario": 500.0
        }
      ]
    },
    "localidade": "RS"
  }'
```

### Calcular impostos para SP (federal + estadual)
```bash
curl -X POST http://localhost:8081/api/impostos/calcular \
  -H "Content-Type: application/json" \
  -d '{
    "orcamento": {
      "id": 2,
      "subtotal": 2000.0,
      "imposto": 0.0,
      "total": 0.0,
      "itens": []
    },
    "localidade": "SP"
  }'
```

## 2. Testando o Microserviço de Registro

### Registrar um imposto
```bash
curl -X POST http://localhost:8082/api/registro/impostos \
  -H "Content-Type: application/json" \
  -d '{
    "orcamentoId": 1,
    "valorVendido": 1000.0,
    "valorImposto": 272.5,
    "localidade": "RS",
    "dataEfetivacao": "2025-06-19T10:30:00",
    "detalhesImposto": "PIS/COFINS - Contribuições Federais (9.25%) + ICMS - Imposto sobre Circulação de Mercadorias e Serviços (18%) + ISS - Imposto sobre Serviços (5%)"
  }'
```

### Registrar outro imposto
```bash
curl -X POST http://localhost:8082/api/registro/impostos \
  -H "Content-Type: application/json" \
  -d '{
    "orcamentoId": 2,
    "valorVendido": 2000.0,
    "valorImposto": 545.0,
    "localidade": "SP",
    "dataEfetivacao": "2025-06-19T11:15:00",
    "detalhesImposto": "PIS/COFINS - Contribuições Federais (9.25%) + ICMS - Imposto sobre Circulação de Mercadorias e Serviços (18%)"
  }'
```

### Consultar relatório mensal (junho/2025)
```bash
curl http://localhost:8082/api/registro/relatorio/2025/6
```

### Listar todos os registros
```bash
curl http://localhost:8082/api/registro/impostos
```

### Buscar registros por período
```bash
curl "http://localhost:8082/api/registro/impostos/periodo?inicio=2025-06-01T00:00:00&fim=2025-06-30T23:59:59"
```

## 3. Verificação de Saúde dos Serviços

### Microserviço de Impostos
```bash
curl http://localhost:8081/api/impostos/health
```

### Microserviço de Registro
```bash
curl http://localhost:8082/api/registro/health
```

## 4. Exemplos de Respostas Esperadas

### Resposta do cálculo de impostos (RS):
```json
{
  "valorImposto": 272.5,
  "localidade": "RS",
  "detalhes": "PIS/COFINS - Contribuições Federais (9.25%) + ICMS - Imposto sobre Circulação de Mercadorias e Serviços (18%) + ISS - Imposto sobre Serviços (5%)"
}
```

### Resposta do registro de imposto:
```json
{
  "id": 1,
  "orcamentoId": 1,
  "valorVendido": 1000.0,
  "valorImposto": 272.5,
  "localidade": "RS",
  "dataEfetivacao": "2025-06-19T10:30:00",
  "detalhesImposto": "PIS/COFINS - Contribuições Federais (9.25%) + ICMS - Imposto sobre Circulação de Mercadorias e Serviços (18%) + ISS - Imposto sobre Serviços (5%)"
}
```

### Resposta do relatório mensal:
```json
{
  "mes": 6,
  "ano": 2025,
  "totalVendido": 3000.0,
  "totalImpostos": 817.5,
  "quantidadeOrcamentos": 2
}
```

## 5. Script de Teste Automatizado

Crie um arquivo `test-microservices.sh`:

```bash
#!/bin/bash

echo "=== Testando Microserviços ==="

# Testar cálculo de impostos
echo "1. Testando cálculo de impostos para RS..."
curl -X POST http://localhost:8081/api/impostos/calcular \
  -H "Content-Type: application/json" \
  -d '{
    "orcamento": {"subtotal": 1000.0, "itens": []},
    "localidade": "RS"
  }' | jq .

echo -e "\n2. Registrando imposto..."
curl -X POST http://localhost:8082/api/registro/impostos \
  -H "Content-Type: application/json" \
  -d '{
    "orcamentoId": 1,
    "valorVendido": 1000.0,
    "valorImposto": 272.5,
    "localidade": "RS"
  }' | jq .

echo -e "\n3. Consultando relatório mensal..."
curl http://localhost:8082/api/registro/relatorio/2025/6 | jq .

echo -e "\n=== Testes concluídos ==="
```

Execute com: `chmod +x test-microservices.sh && ./test-microservices.sh`

