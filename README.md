# 🤖 Curador de Notícias Inteligente

Um agente inteligente desenvolvido em Java usando o Google ADK (Agent Development Kit) que busca, filtra, categoriza e resume notícias sobre tópicos de interesse.

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Pré-requisitos](#pré-requisitos)
- [Instalação e Configuração](#instalação-e-configuração)
- [Como Executar](#como-executar)
- [Como Usar](#como-usar)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Configurações](#configurações)
- [Exemplos de Uso](#exemplos-de-uso)
- [Troubleshooting](#troubleshooting)
- [Contribuindo](#contribuindo)

## 🎯 Sobre o Projeto

O **Curador de Notícias Inteligente** é um agente de IA que automatiza o processo de curadoria de notícias. Ele utiliza o Google ADK para orquestrar diferentes ferramentas especializadas que trabalham em conjunto para:

1. **Buscar** notícias sobre tópicos específicos
2. **Categorizar** automaticamente as notícias encontradas
3. **Gerar resumos** inteligentes de cada artigo
4. **Criar relatórios** estruturados com estatísticas
5. **Avaliar** a relevância de cada notícia

## ✨ Funcionalidades

### 🔍 Busca Inteligente
- Busca por tópico único ou múltiplos tópicos
- Filtros personalizáveis (número máximo de artigos, score mínimo)
- Suporte a diferentes idiomas e regiões

### 🏷️ Categorização Automática
- Categorização automática em: tecnologia, política, economia, esportes, saúde, ciência
- Sistema de palavras-chave inteligente
- Categorias personalizáveis

### 📝 Resumos Inteligentes
- Geração automática de resumos usando IA
- Controle de tamanho dos resumos
- Análise de sentimento (positivo, negativo, neutro)

### 📊 Relatórios Estruturados
- Relatórios em formato texto e JSON
- Estatísticas detalhadas por categoria
- Resumo executivo automático
- Scores de relevância

### 🎛️ Interface Amigável
- Interface de linha de comando intuitiva
- Menu interativo com múltiplas opções
- Modo demonstração para testes rápidos

## 🛠️ Pré-requisitos

### Software Necessário

1. **Java Development Kit (JDK) 17 ou superior**
   ```bash
   # Verificar versão do Java
   java -version
   javac -version
   ```

2. **Apache Maven 3.6 ou superior**
   ```bash
   # Verificar versão do Maven
   mvn -version
   ```

3. **Git** (para clonar o repositório)
   ```bash
   # Verificar versão do Git
   git --version
   ```

### Contas e APIs (Opcional para versão completa)

- **Conta Google Cloud** (para usar Gemini API)
- **Chave de API do Google** (para funcionalidades avançadas)
- **API de Notícias** (NewsAPI, Google News API, etc.)

> **Nota**: O projeto inclui dados simulados para demonstração, então você pode testá-lo sem configurar APIs externas.

## 🚀 Instalação e Configuração

### 1. Clone o Repositório

```bash
git clone <url-do-repositorio>
cd news-curator-agent
```

### 2. Configure as Dependências

O projeto usa Maven para gerenciamento de dependências. Todas as dependências necessárias estão definidas no `pom.xml`.

```bash
# Baixar dependências
mvn clean install
```

### 3. Configure as Propriedades (Opcional)

Edite o arquivo `src/main/resources/application.properties`:

```properties
# Configurações da API do Google (opcional para demonstração)
google.api.key=SUA_CHAVE_API_AQUI
google.project.id=SEU_PROJECT_ID_AQUI

# Configurações de busca
news.search.max.results=10
news.search.language=pt
news.search.country=BR

# Configurações de processamento
news.summary.max.length=200
news.categories=tecnologia,política,economia,esportes,saúde,ciência
```

> **Importante**: Para a demonstração, você pode deixar as configurações padrão. O agente funcionará com dados simulados.

## ▶️ Como Executar

### Método 1: Usando Maven (Recomendado)

```bash
# Executar a aplicação
mvn exec:java -Dexec.mainClass="com.example.newscurator.NewsCuratorApplication"
```

### Método 2: Compilar e Executar JAR

```bash
# Compilar o projeto
mvn clean package

# Executar o JAR gerado
java -jar target/news-curator-agent-1.0.0.jar
```

### Método 3: Executar via IDE

1. Importe o projeto em sua IDE favorita (IntelliJ IDEA, Eclipse, VS Code)
2. Execute a classe `NewsCuratorApplication.java`

## 🎮 Como Usar

### Interface Principal

Ao executar o agente, você verá o menu principal:

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
```

### Opções de Uso

#### 1. 🔍 Busca por Tópico Único
- Digite um tópico de interesse (ex: "inteligência artificial")
- O agente buscará e processará notícias relacionadas

#### 2. 📊 Busca por Múltiplos Tópicos
- Digite vários tópicos separados por vírgula
- Exemplo: "tecnologia, startups, inovação"

#### 3. ⚙️ Busca Personalizada
- Configure filtros avançados:
  - Número máximo de artigos
  - Score mínimo de relevância
  - Tópicos específicos

#### 4. 🎯 Demonstração
- Executa uma busca pré-configurada para demonstrar as funcionalidades
- Ideal para primeiros testes

### Formatos de Relatório

O agente oferece três formatos de saída:

1. **📄 Relatório Completo**: Texto estruturado com todos os detalhes
2. **📋 Resumo Executivo**: Visão geral concisa dos resultados
3. **💾 JSON**: Formato estruturado para integração com outros sistemas

## 📁 Estrutura do Projeto

```
news-curator-agent/
├── src/
│   ├── main/
│   │   ├── java/com/example/newscurator/
│   │   │   ├── agent/
│   │   │   │   └── NewsCuratorAgent.java          # Agente principal
│   │   │   ├── models/
│   │   │   │   ├── NewsArticle.java               # Modelo de artigo
│   │   │   │   └── NewsReport.java                # Modelo de relatório
│   │   │   ├── tools/
│   │   │   │   ├── NewsSearchTool.java            # Ferramenta de busca
│   │   │   │   ├── NewsSummarizerTool.java        # Ferramenta de resumo
│   │   │   │   ├── NewsCategorizerTool.java       # Ferramenta de categorização
│   │   │   │   └── ReportGeneratorTool.java       # Gerador de relatórios
│   │   │   ├── utils/
│   │   │   │   └── ConfigManager.java             # Gerenciador de configurações
│   │   │   └── NewsCuratorApplication.java        # Aplicação principal
│   │   └── resources/
│   │       └── application.properties             # Configurações
│   └── test/
│       └── java/                                  # Testes unitários
├── pom.xml                                        # Configuração Maven
└── README.md                                      # Este arquivo
```

## ⚙️ Configurações

### Arquivo application.properties

```properties
# APIs e Autenticação
google.api.key=YOUR_GOOGLE_API_KEY_HERE
google.project.id=YOUR_PROJECT_ID_HERE

# Configurações de Busca
news.search.max.results=10        # Máximo de artigos por busca
news.search.language=pt           # Idioma das notícias
news.search.country=BR            # País de origem

# Processamento de Texto
news.summary.max.length=200       # Tamanho máximo do resumo
news.categories=tecnologia,política,economia,esportes,saúde,ciência

# Cache e Performance
cache.enabled=true                # Habilitar cache
cache.duration.minutes=30         # Duração do cache

# Logging
logging.level=INFO                # Nível de log
logging.file.path=logs/news-curator.log
```

### Personalização de Categorias

Para adicionar novas categorias, edite o arquivo `NewsCategorizerTool.java` e adicione:

1. Novas palavras-chave no método correspondente
2. Lógica de categorização no método `categorizeArticle`
3. Atualize a configuração `news.categories`

## 📚 Exemplos de Uso

### Exemplo 1: Busca Simples

```bash
# Executar o agente
mvn exec:java -Dexec.mainClass="com.example.newscurator.NewsCuratorApplication"

# No menu, escolher opção 1
# Digite: inteligência artificial
```

**Resultado esperado:**
```
📰 Relatório de Notícias: inteligência artificial
📊 5 artigos encontrados
⭐ Score médio: 0.75
🏷️ Categorias: tecnologia
```

### Exemplo 2: Busca Múltipla

```bash
# Escolher opção 2
# Digite: tecnologia, startups, inovação
```

**Resultado esperado:**
```
📰 Relatório de Notícias: tecnologia, startups, inovação
📊 12 artigos encontrados
⭐ Score médio: 0.68
🏷️ Categorias: tecnologia, economia
```

### Exemplo 3: Busca Personalizada

```bash
# Escolher opção 3
# Tópicos: machine learning, ia
# Máximo de artigos: 5
# Score mínimo: 0.7
```

## 🔧 Troubleshooting

### Problemas Comuns

#### 1. Erro de Compilação
```bash
# Limpar e recompilar
mvn clean compile
```

#### 2. Dependências Não Encontradas
```bash
# Forçar download das dependências
mvn dependency:resolve
```

#### 3. Erro de Memória
```bash
# Aumentar memória da JVM
export MAVEN_OPTS="-Xmx1024m"
mvn exec:java -Dexec.mainClass="com.example.newscurator.NewsCuratorApplication"
```

#### 4. Problemas de Encoding
```bash
# Definir encoding UTF-8
export JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"
```

### Logs e Debugging

Os logs são salvos em `logs/news-curator.log`. Para debugging:

1. Altere `logging.level=DEBUG` no `application.properties`
2. Execute novamente o agente
3. Verifique os logs detalhados

### Verificação de Saúde

Para verificar se tudo está funcionando:

```bash
# Executar testes
mvn test

# Verificar dependências
mvn dependency:tree
```

## 🧪 Testando o Agente

### Teste Rápido (Demonstração)

1. Execute o agente
2. Escolha opção 4 (Demonstração)
3. Observe os resultados com tópicos pré-definidos

### Teste Personalizado

1. Execute o agente
2. Escolha opção 3 (Busca personalizada)
3. Configure:
   - Tópicos: "tecnologia, ia"
   - Máximo: 3 artigos
   - Score mínimo: 0.5

### Teste de Categorização

1. Use tópicos específicos como:
   - "política brasileira" → deve categorizar como "política"
   - "startup tecnologia" → deve categorizar como "tecnologia"
   - "economia brasil" → deve categorizar como "economia"

## 🔮 Próximos Passos

### Melhorias Sugeridas

1. **Integração com APIs Reais**
   - Implementar NewsAPI.org
   - Integrar Google News API
   - Adicionar Bing News Search

2. **Interface Web**
   - Criar interface web com Spring Boot
   - Dashboard interativo
   - Visualizações gráficas

3. **Funcionalidades Avançadas**
   - Análise de tendências
   - Alertas automáticos
   - Exportação para PDF
   - Integração com redes sociais

4. **Machine Learning**
   - Modelo próprio de categorização
   - Análise de sentimento avançada
   - Detecção de fake news

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 📞 Suporte

Se você encontrar problemas ou tiver dúvidas:

1. Verifique a seção [Troubleshooting](#troubleshooting)
2. Consulte os logs em `logs/news-curator.log`
3. Abra uma issue no repositório

---

**Desenvolvido com ❤️ usando Google ADK**

