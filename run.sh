#!/bin/bash

# Script de execução rápida para o Curador de Notícias Inteligente
# Este script facilita a execução do agente sem precisar lembrar dos comandos Maven

echo "🤖 Iniciando Curador de Notícias Inteligente..."
echo "================================================"

# Verificar se Java está instalado
if ! command -v java &> /dev/null; then
    echo "❌ Java não encontrado. Por favor, instale Java 17 ou superior."
    exit 1
fi

# Verificar se Maven está instalado
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven não encontrado. Por favor, instale Maven 3.6 ou superior."
    exit 1
fi

# Verificar versão do Java
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "⚠️  Aviso: Java 17+ recomendado. Versão atual: $JAVA_VERSION"
fi

echo "✅ Pré-requisitos verificados"
echo "📦 Compilando projeto..."

# Compilar o projeto
mvn clean compile -q

if [ $? -ne 0 ]; then
    echo "❌ Erro na compilação. Verifique os logs acima."
    exit 1
fi

echo "✅ Compilação concluída"
echo "🚀 Executando agente..."
echo ""

# Executar o agente
mvn exec:java -Dexec.mainClass="com.example.newscurator.NewsCuratorApplication" -q

echo ""
echo "👋 Agente finalizado. Obrigado por usar o Curador de Notícias!"

