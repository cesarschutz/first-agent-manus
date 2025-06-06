package com.example.newscurator.tools;

import com.example.newscurator.models.NewsArticle;
import com.example.newscurator.models.NewsReport;
import com.example.newscurator.utils.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Ferramenta para gerar relatórios estruturados de notícias
 */
public class ReportGeneratorTool {
    
    private static final Logger logger = LoggerFactory.getLogger(ReportGeneratorTool.class);
    private final ConfigManager configManager;
    
    public ReportGeneratorTool(ConfigManager configManager) {
        this.configManager = configManager;
        logger.info("ReportGeneratorTool inicializada");
    }
    
    /**
     * Gera um relatório completo de notícias
     * 
     * @param topics Tópicos pesquisados
     * @param articles Lista de artigos processados
     * @return Relatório estruturado
     */
    public NewsReport generateReport(List<String> topics, List<NewsArticle> articles) {
        logger.info("Gerando relatório para {} tópicos e {} artigos", topics.size(), articles.size());
        
        try {
            NewsReport report = new NewsReport();
            
            // Configurações básicas do relatório
            report.setTitle(generateReportTitle(topics));
            report.setTopics(topics);
            report.setArticles(articles);
            
            // Gera resumo executivo
            String summary = generateExecutiveSummary(topics, articles);
            report.setSummary(summary);
            
            // Gera estatísticas por categoria
            Map<String, Integer> categorySummary = generateCategorySummary(articles);
            report.setCategorySummary(categorySummary);
            
            logger.info("Relatório gerado com sucesso: {}", report.getTitle());
            return report;
            
        } catch (Exception e) {
            logger.error("Erro ao gerar relatório", e);
            throw new RuntimeException("Falha na geração do relatório: " + e.getMessage(), e);
        }
    }
    
    /**
     * Gera relatório em formato texto para exibição
     */
    public String generateTextReport(NewsReport report) {
        StringBuilder sb = new StringBuilder();
        
        // Cabeçalho
        sb.append("=".repeat(60)).append("\n");
        sb.append(report.getTitle()).append("\n");
        sb.append("=".repeat(60)).append("\n");
        sb.append("Gerado em: ").append(report.getGeneratedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n");
        sb.append("Tópicos: ").append(String.join(", ", report.getTopics())).append("\n");
        sb.append("\n");
        
        // Resumo executivo
        sb.append("RESUMO EXECUTIVO\n");
        sb.append("-".repeat(20)).append("\n");
        sb.append(report.getSummary()).append("\n\n");
        
        // Estatísticas
        sb.append("ESTATÍSTICAS\n");
        sb.append("-".repeat(20)).append("\n");
        sb.append("Total de artigos: ").append(report.getTotalArticles()).append("\n");
        sb.append("Score médio de relevância: ").append(String.format("%.2f", report.getAverageRelevanceScore())).append("\n");
        sb.append("\n");
        
        // Distribuição por categoria
        sb.append("DISTRIBUIÇÃO POR CATEGORIA\n");
        sb.append("-".repeat(30)).append("\n");
        if (report.getCategorySummary() != null) {
            report.getCategorySummary().forEach((category, count) -> 
                sb.append("- ").append(category).append(": ").append(count).append(" artigos\n")
            );
        }
        sb.append("\n");
        
        // Lista de artigos
        sb.append("ARTIGOS ENCONTRADOS\n");
        sb.append("-".repeat(25)).append("\n");
        
        if (report.getArticles() != null) {
            for (int i = 0; i < report.getArticles().size(); i++) {
                NewsArticle article = report.getArticles().get(i);
                sb.append(String.format("%d. %s\n", i + 1, article.getTitle()));
                sb.append(String.format("   Fonte: %s | Categoria: %s | Score: %.2f\n", 
                         article.getSource(), article.getCategory(), article.getRelevanceScore()));
                if (article.getSummary() != null && !article.getSummary().isEmpty()) {
                    sb.append(String.format("   Resumo: %s\n", article.getSummary()));
                }
                if (article.getUrl() != null && !article.getUrl().isEmpty()) {
                    sb.append(String.format("   URL: %s\n", article.getUrl()));
                }
                sb.append("\n");
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Gera relatório em formato JSON
     */
    public String generateJsonReport(NewsReport report) {
        try {
            // Em uma implementação real, usaria Jackson ObjectMapper
            // Por simplicidade, geramos JSON manualmente
            StringBuilder json = new StringBuilder();
            json.append("{\n");
            json.append("  \"title\": \"").append(escapeJson(report.getTitle())).append("\",\n");
            json.append("  \"generatedAt\": \"").append(report.getGeneratedAt().toString()).append("\",\n");
            json.append("  \"topics\": [");
            
            if (report.getTopics() != null) {
                json.append(report.getTopics().stream()
                    .map(topic -> "\"" + escapeJson(topic) + "\"")
                    .collect(Collectors.joining(", ")));
            }
            
            json.append("],\n");
            json.append("  \"totalArticles\": ").append(report.getTotalArticles()).append(",\n");
            json.append("  \"averageRelevanceScore\": ").append(report.getAverageRelevanceScore()).append(",\n");
            json.append("  \"summary\": \"").append(escapeJson(report.getSummary())).append("\"\n");
            json.append("}");
            
            return json.toString();
            
        } catch (Exception e) {
            logger.error("Erro ao gerar relatório JSON", e);
            return "{\"error\": \"Falha na geração do relatório JSON\"}";
        }
    }
    
    /**
     * Gera título do relatório baseado nos tópicos
     */
    private String generateReportTitle(List<String> topics) {
        if (topics.isEmpty()) {
            return "Relatório de Notícias - " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        
        if (topics.size() == 1) {
            return "Relatório de Notícias: " + topics.get(0);
        }
        
        return "Relatório de Notícias: " + String.join(", ", topics);
    }
    
    /**
     * Gera resumo executivo do relatório
     */
    private String generateExecutiveSummary(List<String> topics, List<NewsArticle> articles) {
        if (articles.isEmpty()) {
            return "Nenhuma notícia foi encontrada para os tópicos pesquisados.";
        }
        
        StringBuilder summary = new StringBuilder();
        
        // Informações gerais
        summary.append(String.format("Este relatório apresenta %d notícias relacionadas aos tópicos: %s. ", 
                                    articles.size(), String.join(", ", topics)));
        
        // Análise de relevância
        double avgScore = articles.stream().mapToDouble(NewsArticle::getRelevanceScore).average().orElse(0.0);
        if (avgScore > 0.7) {
            summary.append("As notícias apresentam alta relevância para os tópicos pesquisados. ");
        } else if (avgScore > 0.5) {
            summary.append("As notícias apresentam relevância moderada para os tópicos pesquisados. ");
        } else {
            summary.append("As notícias apresentam relevância variada para os tópicos pesquisados. ");
        }
        
        // Análise de categorias
        Map<String, Long> categoryCount = articles.stream()
            .collect(Collectors.groupingBy(NewsArticle::getCategory, Collectors.counting()));
        
        if (!categoryCount.isEmpty()) {
            String topCategory = categoryCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("geral");
            
            summary.append(String.format("A categoria mais representada é '%s' com %d artigos. ", 
                                        topCategory, categoryCount.get(topCategory)));
        }
        
        // Análise temporal
        summary.append("As notícias foram coletadas e processadas em tempo real, ");
        summary.append("garantindo informações atualizadas sobre os tópicos de interesse.");
        
        return summary.toString();
    }
    
    /**
     * Gera estatísticas por categoria
     */
    private Map<String, Integer> generateCategorySummary(List<NewsArticle> articles) {
        return articles.stream()
            .collect(Collectors.groupingBy(
                NewsArticle::getCategory,
                Collectors.collectingAndThen(Collectors.counting(), Math::toIntExact)
            ));
    }
    
    /**
     * Escapa caracteres especiais para JSON
     */
    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
    
    /**
     * Gera relatório resumido para exibição rápida
     */
    public String generateQuickSummary(NewsReport report) {
        return String.format(
            "📰 %s\n" +
            "📊 %d artigos encontrados\n" +
            "⭐ Score médio: %.2f\n" +
            "🏷️ Categorias: %s",
            report.getTitle(),
            report.getTotalArticles(),
            report.getAverageRelevanceScore(),
            report.getCategorySummary() != null ? 
                String.join(", ", report.getCategorySummary().keySet()) : "N/A"
        );
    }
}

