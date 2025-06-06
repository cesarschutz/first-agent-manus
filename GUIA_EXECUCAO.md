# 🚀 Guia de Execução - Curador de Notícias Inteligente

Este guia fornece instruções passo a passo para executar e testar o agente Curador de Notícias Inteligente.

## 📋 Pré-requisitos Rápidos

Antes de começar, certifique-se de ter:

- ✅ **Java 17+** instalado
- ✅ **Maven 3.6+** instalado
- ✅ **Terminal/Prompt de comando** disponível

### Verificação Rápida

```bash
# Verificar Java
java -version
# Deve mostrar versão 17 ou superior

# Verificar Maven
mvn -version
# Deve mostrar versão 3.6 ou superior
```

## 🎯 Execução Passo a Passo

### Passo 1: Preparar o Ambiente

```bash
# 1. Navegar para o diretório do projeto
cd news-curator-agent

# 2. Verificar estrutura do projeto
ls -la
# Deve mostrar: pom.xml, src/, README.md

# 3. Baixar dependências
mvn clean install
```

### Passo 2: Executar o Agente

```bash
# Método recomendado - usando Maven
mvn exec:java -Dexec.mainClass="com.example.newscurator.NewsCuratorApplication"
```

**Alternativa - Compilar JAR primeiro:**
```bash
# Compilar
mvn clean package

# Executar JAR
java -jar target/news-curator-agent-1.0.0.jar
```

### Passo 3: Interagir com o Agente

Quando o agente iniciar, você verá:

```
╔══════════════════════════════════════════════════════════╗
║              🤖 CURADOR DE NOTÍCIAS INTELIGENTE          ║
║                     Powered by Google ADK               ║
╚══════════════════════════════════════════════════════════╝

==================================================
📰 MENU PRINCIPAL
==================================================
1. 🔍 Buscar notícias sobre um tópico
2. 📊 Buscar notícias sobre múltiplos tópicos
3. ⚙️  Busca personalizada (com filtros)
4. 🎯 Demonstração (tópicos pré-definidos)
5. 📈 Estatísticas do agente
6. ❓ Ajuda
0. 🚪 Sair
==================================================
Escolha uma opção:
```

## 🧪 Cenários de Teste

### Teste 1: Demonstração Rápida (Recomendado para Primeiro Uso)

1. **Execute o agente**
2. **Digite `4`** (Demonstração)
3. **Pressione Enter**

**O que acontece:**
- O agente busca notícias sobre "inteligência artificial", "tecnologia", "inovação"
- Processa e categoriza os artigos
- Gera resumos automáticos
- Apresenta relatório estruturado

**Resultado esperado:**
```
🎯 DEMONSTRAÇÃO
--------------------
Executando busca de demonstração com tópicos pré-definidos:
• Inteligência Artificial
• Tecnologia
• Inovação

🔄 Executando demonstração...
⏳ Aguarde, isso pode levar alguns segundos...

✅ Busca concluída com sucesso!

📰 Relatório de Notícias: inteligência artificial, tecnologia, inovação
📊 8 artigos encontrados
⭐ Score médio: 0.72
🏷️ Categorias: tecnologia
```

### Teste 2: Busca por Tópico Único

1. **Digite `1`** (Buscar notícias sobre um tópico)
2. **Digite um tópico:** `machine learning`
3. **Pressione Enter**
4. **Escolha formato do relatório:** `1` (Relatório completo)

**Resultado esperado:**
```
🔍 BUSCA POR TÓPICO ÚNICO
------------------------------
Digite o tópico que deseja pesquisar: machine learning

🔄 Buscando notícias sobre: machine learning
⏳ Aguarde, isso pode levar alguns segundos...

✅ Busca concluída com sucesso!

📰 Relatório de Notícias: machine learning
📊 5 artigos encontrados
⭐ Score médio: 0.78
🏷️ Categorias: tecnologia

Como deseja visualizar o relatório?
1. 📄 Relatório completo (texto)
2. 📋 Resumo executivo
3. 💾 Salvar como JSON
Escolha (1-3): 1

============================================================
Relatório de Notícias: machine learning
============================================================
Gerado em: 06/06/2025 15:30
Tópicos: machine learning

RESUMO EXECUTIVO
--------------------
Este relatório apresenta 5 notícias relacionadas aos tópicos: machine learning...
```

### Teste 3: Busca Múltipla

1. **Digite `2`** (Buscar notícias sobre múltiplos tópicos)
2. **Digite tópicos:** `startups, tecnologia, inovação`
3. **Pressione Enter**
4. **Escolha formato:** `2` (Resumo executivo)

### Teste 4: Busca Personalizada

1. **Digite `3`** (Busca personalizada)
2. **Configure:**
   - **Tópicos:** `inteligência artificial, robótica`
   - **Máximo de artigos:** `3`
   - **Score mínimo:** `0.6`
3. **Pressione Enter**

### Teste 5: Verificar Estatísticas

1. **Digite `5`** (Estatísticas do agente)
2. **Observe as informações do sistema**

**Resultado esperado:**
```
📈 ESTATÍSTICAS DO AGENTE
------------------------------
NewsCuratorAgent Stats:
- Ferramentas ativas: 4
- Categorias suportadas: tecnologia, política, economia, esportes, saúde, ciência
- Máximo de resultados: 10
- Idioma: pt
```

## 🔍 Como Funciona o Google ADK

### Arquitetura do Agente

O projeto demonstra conceitos fundamentais do Google ADK:

#### 1. **Agente Principal** (`NewsCuratorAgent`)
```java
// Coordena todas as ferramentas
public class NewsCuratorAgent {
    private final NewsSearchTool searchTool;
    private final NewsSummarizerTool summarizerTool;
    private final NewsCategorizerTool categorizerTool;
    private final ReportGeneratorTool reportGeneratorTool;
    
    // Orquestra o workflow completo
    public NewsReport curateNews(List<String> topics) {
        // 1. Buscar notícias
        // 2. Categorizar
        // 3. Resumir
        // 4. Gerar relatório
    }
}
```

#### 2. **Ferramentas Especializadas** (Tools)
- **NewsSearchTool**: Busca notícias (simula APIs reais)
- **NewsCategorizerTool**: Categoriza automaticamente
- **NewsSummarizerTool**: Gera resumos inteligentes
- **ReportGeneratorTool**: Cria relatórios estruturados

#### 3. **Modelos de Dados** (Models)
- **NewsArticle**: Representa um artigo de notícia
- **NewsReport**: Representa um relatório completo

#### 4. **Configuração** (ConfigManager)
- Gerencia todas as configurações do agente
- Permite personalização sem recompilação

### Fluxo de Execução ADK

```
Usuário Input → NewsCuratorAgent → Tools → Models → Output
     ↓              ↓                ↓        ↓        ↓
  "tecnologia" → Coordenação → Busca/Resumo → Dados → Relatório
```

### Conceitos ADK Demonstrados

1. **Orquestração de Agentes**: Como coordenar múltiplas ferramentas
2. **Modularidade**: Cada ferramenta tem responsabilidade específica
3. **Configurabilidade**: Sistema flexível de configurações
4. **Processamento Sequencial**: Workflow estruturado
5. **Tratamento de Erros**: Robustez em cenários de falha

## 🛠️ Personalização e Extensão

### Adicionar Nova Categoria

1. **Edite `NewsCategorizerTool.java`:**
```java
private List<String> getEducationKeywords() {
    return List.of("educação", "escola", "universidade", "ensino");
}
```

2. **Atualize o método de categorização:**
```java
if (containsKeywords(title, getEducationKeywords())) {
    return "educação";
}
```

3. **Atualize `application.properties`:**
```properties
news.categories=tecnologia,política,economia,esportes,saúde,ciência,educação
```

### Integrar API Real

Para conectar com APIs reais, edite `NewsSearchTool.java`:

```java
private List<NewsArticle> callRealNewsAPI(String topic) {
    // Implementar chamada HTTP para NewsAPI.org
    // ou Google News API
    // Retornar lista de NewsArticle
}
```

### Adicionar Nova Ferramenta

1. **Crie nova classe em `tools/`:**
```java
public class SentimentAnalysisTool {
    public String analyzeSentiment(NewsArticle article) {
        // Implementar análise de sentimento
    }
}
```

2. **Integre no agente principal:**
```java
private final SentimentAnalysisTool sentimentTool;
```

## 🐛 Troubleshooting Específico

### Problema: "Comando mvn não encontrado"

**Solução:**
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install maven

# macOS
brew install maven

# Windows
# Baixar de https://maven.apache.org/download.cgi
```

### Problema: "Java version incompatível"

**Solução:**
```bash
# Verificar versões disponíveis
java -version

# Ubuntu - instalar Java 17
sudo apt install openjdk-17-jdk

# Definir JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
```

### Problema: "Encoding UTF-8"

**Solução:**
```bash
# Definir encoding antes de executar
export JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"
mvn exec:java -Dexec.mainClass="com.example.newscurator.NewsCuratorApplication"
```

### Problema: "Memória insuficiente"

**Solução:**
```bash
# Aumentar memória da JVM
export MAVEN_OPTS="-Xmx2048m -Xms512m"
mvn exec:java -Dexec.mainClass="com.example.newscurator.NewsCuratorApplication"
```

## 📊 Interpretando os Resultados

### Score de Relevância
- **0.8 - 1.0**: Alta relevância (artigo muito relacionado ao tópico)
- **0.6 - 0.8**: Relevância moderada (artigo relacionado)
- **0.4 - 0.6**: Baixa relevância (artigo parcialmente relacionado)
- **0.0 - 0.4**: Relevância mínima (artigo pouco relacionado)

### Categorias Automáticas
- **tecnologia**: IA, software, startups, inovação
- **política**: governo, eleições, leis, reformas
- **economia**: mercado, investimentos, PIB, inflação
- **esportes**: futebol, olimpíadas, competições
- **saúde**: medicina, tratamentos, pesquisas médicas
- **ciência**: descobertas, pesquisas, universidades

### Formatos de Saída

1. **Relatório Completo**: Ideal para análise detalhada
2. **Resumo Executivo**: Perfeito para visão geral rápida
3. **JSON**: Útil para integração com outros sistemas

## 🎓 Aprendendo Google ADK

Este projeto demonstra conceitos fundamentais do ADK:

### 1. **Agent Pattern**
- Como estruturar um agente inteligente
- Coordenação de múltiplas ferramentas
- Gerenciamento de estado e configuração

### 2. **Tool Integration**
- Como criar ferramentas especializadas
- Integração entre diferentes componentes
- Reutilização de código

### 3. **Data Flow**
- Como dados fluem entre componentes
- Transformação e processamento de dados
- Geração de relatórios estruturados

### 4. **Error Handling**
- Tratamento robusto de erros
- Fallbacks e recuperação
- Logging e debugging

### 5. **Configuration Management**
- Sistema flexível de configurações
- Personalização sem recompilação
- Diferentes ambientes (dev, prod)

## 🚀 Próximos Passos

Após dominar este exemplo, você pode:

1. **Integrar APIs Reais**: NewsAPI, Google News, Bing News
2. **Adicionar Interface Web**: Spring Boot + React
3. **Implementar Cache**: Redis ou banco de dados
4. **Adicionar Testes**: JUnit, Mockito
5. **Deploy na Nuvem**: Google Cloud, AWS, Azure
6. **Criar Outros Agentes**: Baseado nesta estrutura

---

**🎉 Parabéns! Você agora tem um agente inteligente funcionando com Google ADK!**

