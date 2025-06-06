package com.example.newscurator;

import com.example.newscurator.agent.NewsCuratorAgent;
import com.example.newscurator.models.NewsReport;
import com.example.newscurator.tools.ReportGeneratorTool;
import com.example.newscurator.utils.ConfigManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Aplicação principal do Curador de Notícias Inteligente
 * 
 * Esta classe fornece uma interface de linha de comando para interagir com o agente
 */
public class NewsCuratorApplication {
    
    private static final Logger logger = LoggerFactory.getLogger(NewsCuratorApplication.class);
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        logger.info("Iniciando Curador de Notícias Inteligente");
        
        try {
            // Inicializa o agente
            NewsCuratorAgent agent = new NewsCuratorAgent();
            ReportGeneratorTool reportTool = new ReportGeneratorTool(new ConfigManager());
            
            // Exibe banner de boas-vindas
            printWelcomeBanner();
            
            // Loop principal da aplicação
            boolean running = true;
            while (running) {
                try {
                    printMainMenu();
                    int choice = getMenuChoice();
                    
                    switch (choice) {
                        case 1:
                            handleSingleTopicSearch(agent, reportTool);
                            break;
                        case 2:
                            handleMultipleTopicsSearch(agent, reportTool);
                            break;
                        case 3:
                            handleCustomSearch(agent, reportTool);
                            break;
                        case 4:
                            handleDemoSearch(agent, reportTool);
                            break;
                        case 5:
                            showAgentStats(agent);
                            break;
                        case 6:
                            showHelp();
                            break;
                        case 0:
                            running = false;
                            break;
                        default:
                            System.out.println("❌ Opção inválida! Tente novamente.");
                    }
                    
                    if (running) {
                        System.out.println("\nPressione Enter para continuar...");
                        scanner.nextLine();
                    }
                    
                } catch (Exception e) {
                    logger.error("Erro durante execução", e);
                    System.out.println("❌ Erro: " + e.getMessage());
                }
            }
            
            System.out.println("👋 Obrigado por usar o Curador de Notícias Inteligente!");
            
        } catch (Exception e) {
            logger.error("Erro fatal na aplicação", e);
            System.err.println("❌ Erro fatal: " + e.getMessage());
            System.exit(1);
        }
    }
    
    /**
     * Exibe banner de boas-vindas
     */
    private static void printWelcomeBanner() {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║              🤖 CURADOR DE NOTÍCIAS INTELIGENTE          ║");
        System.out.println("║                     Powered by Google ADK               ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Bem-vindo ao seu assistente pessoal de curadoria de notícias!");
        System.out.println("Este agente busca, filtra e resume notícias sobre seus tópicos de interesse.");
        System.out.println();
    }
    
    /**
     * Exibe menu principal
     */
    private static void printMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📰 MENU PRINCIPAL");
        System.out.println("=".repeat(50));
        System.out.println("1. 🔍 Buscar notícias sobre um tópico");
        System.out.println("2. 📊 Buscar notícias sobre múltiplos tópicos");
        System.out.println("3. ⚙️  Busca personalizada (com filtros)");
        System.out.println("4. 🎯 Demonstração (tópicos pré-definidos)");
        System.out.println("5. 📈 Estatísticas do agente");
        System.out.println("6. ❓ Ajuda");
        System.out.println("0. 🚪 Sair");
        System.out.println("=".repeat(50));
        System.out.print("Escolha uma opção: ");
    }
    
    /**
     * Obtém escolha do menu do usuário
     */
    private static int getMenuChoice() {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Manipula busca por um único tópico
     */
    private static void handleSingleTopicSearch(NewsCuratorAgent agent, ReportGeneratorTool reportTool) {
        System.out.println("\n🔍 BUSCA POR TÓPICO ÚNICO");
        System.out.println("-".repeat(30));
        System.out.print("Digite o tópico que deseja pesquisar: ");
        
        String topic = scanner.nextLine().trim();
        if (topic.isEmpty()) {
            System.out.println("❌ Tópico não pode estar vazio!");
            return;
        }
        
        System.out.println("\n🔄 Buscando notícias sobre: " + topic);
        System.out.println("⏳ Aguarde, isso pode levar alguns segundos...");
        
        NewsReport report = agent.curateNews(topic);
        displayReport(report, reportTool);
    }
    
    /**
     * Manipula busca por múltiplos tópicos
     */
    private static void handleMultipleTopicsSearch(NewsCuratorAgent agent, ReportGeneratorTool reportTool) {
        System.out.println("\n📊 BUSCA POR MÚLTIPLOS TÓPICOS");
        System.out.println("-".repeat(35));
        System.out.println("Digite os tópicos separados por vírgula:");
        System.out.print("Exemplo: tecnologia, inteligência artificial, startups\n> ");
        
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("❌ Pelo menos um tópico deve ser informado!");
            return;
        }
        
        List<String> topics = Arrays.asList(input.split(","))
            .stream()
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .toList();
        
        if (topics.isEmpty()) {
            System.out.println("❌ Nenhum tópico válido foi informado!");
            return;
        }
        
        System.out.println("\n🔄 Buscando notícias sobre: " + String.join(", ", topics));
        System.out.println("⏳ Aguarde, isso pode levar alguns segundos...");
        
        NewsReport report = agent.curateNews(topics);
        displayReport(report, reportTool);
    }
    
    /**
     * Manipula busca personalizada com filtros
     */
    private static void handleCustomSearch(NewsCuratorAgent agent, ReportGeneratorTool reportTool) {
        System.out.println("\n⚙️ BUSCA PERSONALIZADA");
        System.out.println("-".repeat(25));
        
        // Tópicos
        System.out.print("Digite os tópicos (separados por vírgula): ");
        String topicsInput = scanner.nextLine().trim();
        if (topicsInput.isEmpty()) {
            System.out.println("❌ Pelo menos um tópico deve ser informado!");
            return;
        }
        
        List<String> topics = Arrays.asList(topicsInput.split(","))
            .stream()
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .toList();
        
        // Número máximo de artigos
        System.out.print("Número máximo de artigos (padrão: 10): ");
        String maxArticlesInput = scanner.nextLine().trim();
        int maxArticles = 10;
        if (!maxArticlesInput.isEmpty()) {
            try {
                maxArticles = Integer.parseInt(maxArticlesInput);
                if (maxArticles <= 0) maxArticles = 10;
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Valor inválido, usando padrão: 10");
            }
        }
        
        // Score mínimo de relevância
        System.out.print("Score mínimo de relevância (0.0 a 1.0, padrão: 0.0): ");
        String minScoreInput = scanner.nextLine().trim();
        double minScore = 0.0;
        if (!minScoreInput.isEmpty()) {
            try {
                minScore = Double.parseDouble(minScoreInput);
                if (minScore < 0.0 || minScore > 1.0) minScore = 0.0;
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Valor inválido, usando padrão: 0.0");
            }
        }
        
        System.out.println("\n🔄 Executando busca personalizada...");
        System.out.println("⏳ Aguarde, isso pode levar alguns segundos...");
        
        NewsReport report = agent.curateNews(topics, maxArticles, minScore);
        displayReport(report, reportTool);
    }
    
    /**
     * Executa demonstração com tópicos pré-definidos
     */
    private static void handleDemoSearch(NewsCuratorAgent agent, ReportGeneratorTool reportTool) {
        System.out.println("\n🎯 DEMONSTRAÇÃO");
        System.out.println("-".repeat(20));
        System.out.println("Executando busca de demonstração com tópicos pré-definidos:");
        System.out.println("• Inteligência Artificial");
        System.out.println("• Tecnologia");
        System.out.println("• Inovação");
        
        List<String> demoTopics = Arrays.asList("inteligência artificial", "tecnologia", "inovação");
        
        System.out.println("\n🔄 Executando demonstração...");
        System.out.println("⏳ Aguarde, isso pode levar alguns segundos...");
        
        NewsReport report = agent.curateNews(demoTopics);
        displayReport(report, reportTool);
    }
    
    /**
     * Exibe estatísticas do agente
     */
    private static void showAgentStats(NewsCuratorAgent agent) {
        System.out.println("\n📈 ESTATÍSTICAS DO AGENTE");
        System.out.println("-".repeat(30));
        System.out.println(agent.getAgentStats());
    }
    
    /**
     * Exibe ajuda
     */
    private static void showHelp() {
        System.out.println("\n❓ AJUDA");
        System.out.println("-".repeat(10));
        System.out.println("O Curador de Notícias Inteligente é um agente que:");
        System.out.println();
        System.out.println("🔍 BUSCA notícias sobre tópicos de seu interesse");
        System.out.println("🏷️ CATEGORIZA automaticamente as notícias encontradas");
        System.out.println("📝 GERA RESUMOS inteligentes de cada artigo");
        System.out.println("📊 CRIA RELATÓRIOS estruturados com estatísticas");
        System.out.println("⭐ AVALIA a relevância de cada notícia");
        System.out.println();
        System.out.println("💡 DICAS:");
        System.out.println("• Use termos específicos para melhores resultados");
        System.out.println("• Combine múltiplos tópicos para análises abrangentes");
        System.out.println("• Ajuste filtros na busca personalizada conforme necessário");
        System.out.println("• Experimente a demonstração para ver o agente em ação");
    }
    
    /**
     * Exibe relatório gerado
     */
    private static void displayReport(NewsReport report, ReportGeneratorTool reportTool) {
        System.out.println("\n✅ Busca concluída com sucesso!");
        System.out.println();
        
        // Resumo rápido
        System.out.println(reportTool.generateQuickSummary(report));
        System.out.println();
        
        // Pergunta sobre formato de exibição
        System.out.println("Como deseja visualizar o relatório?");
        System.out.println("1. 📄 Relatório completo (texto)");
        System.out.println("2. 📋 Resumo executivo");
        System.out.println("3. 💾 Salvar como JSON");
        System.out.print("Escolha (1-3): ");
        
        String choice = scanner.nextLine().trim();
        System.out.println();
        
        switch (choice) {
            case "1":
                System.out.println(reportTool.generateTextReport(report));
                break;
            case "2":
                System.out.println("📋 RESUMO EXECUTIVO");
                System.out.println("-".repeat(20));
                System.out.println(report.getSummary());
                break;
            case "3":
                String json = reportTool.generateJsonReport(report);
                System.out.println("💾 RELATÓRIO EM JSON:");
                System.out.println("-".repeat(25));
                System.out.println(json);
                break;
            default:
                System.out.println("📋 RESUMO EXECUTIVO (padrão)");
                System.out.println("-".repeat(30));
                System.out.println(report.getSummary());
        }
    }
}

