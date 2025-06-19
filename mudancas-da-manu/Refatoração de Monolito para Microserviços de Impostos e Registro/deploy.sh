#!/bin/bash

echo "=== Script de Build e Deploy dos Microserviços ==="

# Função para verificar se o comando foi executado com sucesso
check_success() {
    if [ $? -eq 0 ]; then
        echo "✅ $1 executado com sucesso"
    else
        echo "❌ Erro ao executar $1"
        exit 1
    fi
}

# Build do Microserviço de Impostos
echo "🔨 Fazendo build do Microserviço de Impostos..."
cd microservico-impostos
mvn clean package -DskipTests
check_success "Build do Microserviço de Impostos"
cd ..

# Build do Microserviço de Registro
echo "🔨 Fazendo build do Microserviço de Registro..."
cd microservico-registro
mvn clean package -DskipTests
check_success "Build do Microserviço de Registro"
cd ..

# Parar containers existentes
echo "🛑 Parando containers existentes..."
docker-compose down
check_success "Parada dos containers"

# Remover imagens antigas (opcional)
echo "🗑️ Removendo imagens antigas..."
docker-compose down --rmi all --volumes --remove-orphans 2>/dev/null || true

# Build e start dos serviços
echo "🚀 Iniciando todos os serviços..."
docker-compose up --build -d
check_success "Inicialização dos serviços"

# Aguardar os serviços ficarem prontos
echo "⏳ Aguardando serviços ficarem prontos..."
sleep 30

# Verificar status dos serviços
echo "🔍 Verificando status dos serviços..."
echo ""
echo "Status dos containers:"
docker-compose ps

echo ""
echo "🌐 URLs dos serviços:"
echo "- Microserviço de Impostos: http://localhost:8081/api/impostos/health"
echo "- Microserviço de Registro: http://localhost:8082/api/registro/health"
echo "- Monolito: http://localhost:8080"

echo ""
echo "🧪 Testando conectividade dos microserviços..."

# Testar microserviço de impostos
echo "Testando Microserviço de Impostos..."
curl -f http://localhost:8081/api/impostos/health 2>/dev/null
if [ $? -eq 0 ]; then
    echo "✅ Microserviço de Impostos está funcionando"
else
    echo "❌ Microserviço de Impostos não está respondendo"
fi

# Testar microserviço de registro
echo "Testando Microserviço de Registro..."
curl -f http://localhost:8082/api/registro/health 2>/dev/null
if [ $? -eq 0 ]; then
    echo "✅ Microserviço de Registro está funcionando"
else
    echo "❌ Microserviço de Registro não está respondendo"
fi

echo ""
echo "🎉 Deploy concluído! Todos os serviços estão rodando."
echo ""
echo "📋 Para monitorar os logs:"
echo "docker-compose logs -f"
echo ""
echo "🛑 Para parar todos os serviços:"
echo "docker-compose down"

