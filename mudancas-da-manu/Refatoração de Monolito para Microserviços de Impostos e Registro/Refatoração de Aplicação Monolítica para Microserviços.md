# Refatoração de Aplicação Monolítica para Microserviços

## Visão Geral

Este projeto demonstra a refatoração de uma aplicação monolítica de vendas, extraindo o serviço de cálculo de impostos em um microserviço independente e criando um segundo microserviço para registro e consulta de impostos por orçamento aprovado.

## Arquitetura Original vs Refatorada

### Arquitetura Original (Monolítica)
- **Aplicação única**: Todas as funcionalidades em um único projeto Spring Boot
- **Acoplamento forte**: Serviço de impostos integrado diretamente no código
- **Banco de dados único**: Todas as entidades no mesmo banco
- **Escalabilidade limitada**: Toda a aplicação precisa ser escalada em conjunto

### Arquitetura Refatorada (Microserviços)
- **Microserviço de Impostos**: Responsável pelo cálculo de impostos
- **Microserviço de Registro**: Responsável pelo registro e consulta de impostos
- **Monolito Refatorado**: Consome os microserviços via HTTP
- **Bancos independentes**: Cada microserviço tem seu próprio banco de dados

## Componentes da Solução

### 1. Microserviço de Cálculo de Impostos (Porta 8081)

**Responsabilidades:**
- Calcular impostos baseado na localidade
- Aplicar diferentes estratégias de cálculo (Federal, Estadual, Municipal)
- Fornecer detalhes sobre os impostos aplicados

**Endpoints:**
- `POST /api/impostos/calcular` - Calcula impostos para um orçamento
- `GET /api/impostos/health` - Verificação de saúde do serviço

**Tecnologias:**
- Spring Boot 3.1.0
- Java 17
- Padrões: Strategy, Decorator, Factory

### 2. Microserviço de Registro de Impostos (Porta 8082)

**Responsabilidades:**
- Registrar impostos de orçamentos efetivados
- Gerar relatórios mensais de vendas e impostos
- Consultar histórico de impostos por período

**Endpoints:**
- `POST /api/registro/impostos` - Registra imposto de orçamento efetivado
- `GET /api/registro/relatorio/{ano}/{mes}` - Relatório mensal
- `GET /api/registro/impostos` - Lista todos os registros
- `GET /api/registro/impostos/periodo` - Busca por período
- `GET /api/registro/health` - Verificação de saúde do serviço

**Tecnologias:**
- Spring Boot 3.1.0
- Spring Data JPA
- MySQL 8.0
- Java 17

### 3. Monolito Refatorado (Porta 8080)

**Modificações realizadas:**
- `ServicoDeImpostoRefatorado`: Consome o microserviço de impostos via HTTP
- `ServicoDeVendasRefatorado`: Integra com ambos os microserviços
- `EfetivaOrcamentoUCRefatorado`: Registra impostos após efetivação
- Configuração de `RestTemplate` para comunicação HTTP
- Tratamento de fallback em caso de indisponibilidade dos microserviços

## Estrutura do Projeto

```
projeto-refatoracao/
├── microservico-impostos/          # Microserviço de cálculo de impostos
│   ├── src/main/java/
│   │   └── com/bcopstein/microservicoimpostos/
│   │       ├── controller/         # Controllers REST
│   │       ├── service/           # Lógica de negócio
│   │       ├── model/             # Modelos de dados
│   │       └── config/            # Configurações
│   ├── pom.xml
│   └── Dockerfile
├── microservico-registro/          # Microserviço de registro
│   ├── src/main/java/
│   │   └── com/bcopstein/microservicoregistro/
│   │       ├── controller/         # Controllers REST
│   │       ├── service/           # Lógica de negócio
│   │       ├── model/             # Entidades JPA
│   │       ├── repository/        # Repositórios JPA
│   │       └── dto/               # DTOs
│   ├── pom.xml
│   └── Dockerfile
├── monolito-refatorado/            # Classes refatoradas do monolito
│   ├── ServicoDeImpostoRefatorado.java
│   ├── ServicoDeVendasRefatorado.java
│   ├── EfetivaOrcamentoUCRefatorado.java
│   ├── RestTemplateConfig.java
│   └── application-refatorado.properties
├── docker-compose.yml              # Orquestração dos serviços
└── README.md                       # Esta documentação
```

## Padrões de Design Implementados

### 1. Strategy Pattern
Implementado no microserviço de impostos para diferentes tipos de cálculo:
- `ImpostoFederal`: PIS/COFINS (9.25%)
- `ImpostoEstadual`: ICMS (18%)
- `ImpostoMunicipal`: ISS (5%)

### 2. Decorator Pattern
Utilizado para combinar diferentes tipos de impostos:
- `ImpostoComposto`: Permite combinar múltiplos impostos

### 3. Factory Pattern
`ImpostoFactory` cria a estratégia de cálculo baseada na localidade:
- RS: Federal + Estadual + Municipal
- SP: Federal + Estadual
- RJ: Federal + Municipal
- Outros: Apenas Federal

### 4. Circuit Breaker (Implementação Simples)
Fallback implementado nos serviços refatorados em caso de indisponibilidade dos microserviços.

## Banco de Dados

### Microserviço de Registro
Utiliza MySQL com a seguinte estrutura:

```sql
CREATE TABLE registro_impostos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    orcamento_id BIGINT NOT NULL,
    valor_vendido DOUBLE NOT NULL,
    valor_imposto DOUBLE NOT NULL,
    localidade VARCHAR(255) NOT NULL,
    data_efetivacao DATETIME NOT NULL,
    detalhes_imposto TEXT
);
```

## Configuração e Execução

### Pré-requisitos
- Docker e Docker Compose instalados
- Java 17 (para desenvolvimento local)
- Maven 3.6+ (para build local)

### Executando com Docker Compose

1. **Clone o repositório original e aplique as modificações**
2. **Build dos microserviços:**
   ```bash
   # Build do microserviço de impostos
   cd microservico-impostos
   mvn clean package -DskipTests
   cd ..
   
   # Build do microserviço de registro
   cd microservico-registro
   mvn clean package -DskipTests
   cd ..
   ```

3. **Executar todos os serviços:**
   ```bash
   docker-compose up -d
   ```

4. **Verificar status dos serviços:**
   ```bash
   docker-compose ps
   ```

### Verificação dos Serviços

- **Microserviço de Impostos**: http://localhost:8081/api/impostos/health
- **Microserviço de Registro**: http://localhost:8082/api/registro/health
- **Monolito**: http://localhost:8080 (endpoints originais)

## Exemplos de Uso

### 1. Calcular Impostos
```bash
curl -X POST http://localhost:8081/api/impostos/calcular \
  -H "Content-Type: application/json" \
  -d '{
    "orcamento": {
      "subtotal": 1000.0,
      "itens": []
    },
    "localidade": "RS"
  }'
```

### 2. Registrar Imposto
```bash
curl -X POST http://localhost:8082/api/registro/impostos \
  -H "Content-Type: application/json" \
  -d '{
    "orcamentoId": 1,
    "valorVendido": 1000.0,
    "valorImposto": 272.5,
    "localidade": "RS",
    "detalhesImposto": "Imposto calculado para RS"
  }'
```

### 3. Relatório Mensal
```bash
curl http://localhost:8082/api/registro/relatorio/2025/6
```

## Benefícios da Refatoração

### 1. **Escalabilidade Independente**
- Cada microserviço pode ser escalado conforme a demanda
- Microserviço de impostos pode ter mais instâncias em períodos de alta demanda

### 2. **Manutenibilidade**
- Código mais organizado e focado em responsabilidades específicas
- Facilita a manutenção e evolução independente

### 3. **Tecnologias Específicas**
- Cada microserviço pode usar a tecnologia mais adequada
- Banco de dados específico para cada contexto

### 4. **Resiliência**
- Falha em um microserviço não derruba toda a aplicação
- Implementação de fallbacks para cenários de indisponibilidade

### 5. **Deploy Independente**
- Cada microserviço pode ser deployado independentemente
- Reduz riscos de deploy e permite releases mais frequentes

## Considerações de Produção

### 1. **Service Discovery**
Para produção, considere implementar:
- Eureka Server (Netflix)
- Consul
- Kubernetes Service Discovery

### 2. **API Gateway**
Implemente um API Gateway para:
- Roteamento de requisições
- Autenticação e autorização
- Rate limiting
- Monitoramento

### 3. **Monitoramento**
- Implementar health checks mais robustos
- Métricas de performance
- Logs centralizados
- Distributed tracing

### 4. **Segurança**
- Autenticação entre serviços (JWT, OAuth2)
- HTTPS em todas as comunicações
- Secrets management

### 5. **Backup e Recovery**
- Backup automático dos bancos de dados
- Estratégias de disaster recovery
- Testes de recuperação

## Próximos Passos

1. **Implementar Service Discovery**
2. **Adicionar API Gateway**
3. **Implementar Circuit Breaker robusto (Hystrix/Resilience4j)**
4. **Adicionar testes de integração**
5. **Implementar monitoramento com Prometheus/Grafana**
6. **Configurar CI/CD pipeline**
7. **Implementar autenticação e autorização**

## Conclusão

A refatoração demonstra como extrair funcionalidades específicas de um monolito para microserviços independentes, mantendo a funcionalidade original enquanto ganha os benefícios da arquitetura de microserviços. O projeto serve como base para futuras evoluções e pode ser expandido com mais funcionalidades e melhorias de produção.

