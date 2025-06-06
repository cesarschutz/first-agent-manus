package com.example.newscurator.agent;

import com.example.newscurator.models.NewsArticle;
import com.example.newscurator.models.NewsReport;
import com.example.newscurator.tools.NewsSearchTool;
import com.example.newscurator.tools.NewsSummarizerTool;
import com.example.newscurator.tools.NewsCategorizerTool;
import com.example.newscurator.tools.ReportGeneratorTool;
import com.example.newscurator.utils.ConfigManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Agente principal responsável por curar notícias
 * 
 * Este agente coordena diferentes ferramentas para:
 * 1. Buscar notícias sobre tópicos específicos
 * 2. Filtrar e categorizar as notícias
 * 3. Gerar resumos personalizados
 * 4. Criar relatórios estruturados
 */
public class NewsCuratorAgent {
    
    private static final Logger logger = LoggerFactory.getLogger(NewsCuratorAgent.class);
    
    private final NewsSearchTool searchTool;
    private final NewsSummarizerTool summarizerTool;
    private final NewsCategorizerTool categorizerTool;
    private final ReportGeneratorTool reportGeneratorTool;
    private final ConfigManager configManager;
    
    public NewsCuratorAgent() {
        this.configManager = new ConfigManager();
        this.searchTool = new NewsSearchTool(configManager);
        this.summarizerTool = new NewsSummarizerTool(configManager);
        this.categorizerTool = new NewsCategorizerTool(configManager);
        this.reportGeneratorTool = new ReportGeneratorTool(configManager);
        
        logger.info("NewsCuratorAgent inicializado com sucesso");
    }
    
    /**
     * Método principal para curar notícias sobre tópicos específicos
     * 
     * @param topics Lista de tópicos para buscar notícias
     * @return Relatório de notícias curadas
     */
    public NewsReport curateNews(List<String> topics) {
        logger.info("Iniciando curadoria de notícias para tópicos: {}", topics);
        
        try {
            // 1. Buscar notícias para cada tópico
            List<NewsArticle> allArticles = new ArrayList<>();
            for (String topic : topics) {
                logger.info("Buscando notícias para o tópico: {}", topic);
                List<NewsArticle> articles = searchTool.searchNews(topic);
                allArticles.addAll(articles);
            }
            
            logger.info("Total de artigos encontrados: {}", allArticles.size());
            
            // 2. Categorizar as notícias
            logger.info("Categorizando notícias...");
            allArticles = categorizerTool.categorizeArticles(allArticles);
            
            // 3. Gerar resumos para cada artigo
            logger.info("Gerando resumos...");
            allArticles = summarizerTool.summarizeArticles(allArticles);
            
            // 4. Filtrar por relevância (manter apenas os mais relevantes)
            allArticles = filterByRelevance(allArticles);
            
            // 5. Gerar relatório final
            logger.info("Gerando relatório final...");
            NewsReport report = reportGeneratorTool.generateReport(topics, allArticles);
            
            logger.info("Curadoria concluída com sucesso. Relatório gerado com {} artigos", 
                       report.getTotalArticles());
            
            return report;
            
        } catch (Exception e) {
            logger.error("Erro durante a curadoria de notícias", e);
            throw new RuntimeException("Falha na curadoria de notícias: " + e.getMessage(), e);
        }
    }
    
    /**
     * Curar notícias com configurações personalizadas
     */
    public NewsReport curateNews(List<String> topics, int maxArticles, double minRelevanceScore) {
        logger.info("Curadoria personalizada - Tópicos: {}, Max artigos: {}, Score mínimo: {}", 
                   topics, maxArticles, minRelevanceScore);
        
        // Temporariamente ajustar configurações
        int originalMaxResults = configManager.getMaxSearchResults();
        configManager.setMaxSearchResults(maxArticles);
        
        try {
            NewsReport report = curateNews(topics);
            
            // Filtrar por score mínimo
            List<NewsArticle> filteredArticles = report.getArticles().stream()
                .filter(article -> article.getRelevanceScore() >= minRelevanceScore)
                .toList();
            
            report.setArticles(filteredArticles);
            return report;
            
        } finally {
            // Restaurar configuração original
            configManager.setMaxSearchResults(originalMaxResults);
        }
    }
    
    /**
     * Método de conveniência para curar notícias sobre um único tópico
     */
    public NewsReport curateNews(String topic) {
        return curateNews(Arrays.asList(topic));
    }
    
    /**
     * Filtra artigos por relevância, mantendo apenas os mais relevantes
     */
    private List<NewsArticle> filterByRelevance(List<NewsArticle> articles) {
        if (articles.isEmpty()) {
            return articles;
        }
        
        // Ordena por score de relevância (decrescente) e pega os melhores
        int maxResults = configManager.getMaxSearchResults();
        
        return articles.stream()
            .sorted((a, b) -> Double.compare(b.getRelevanceScore(), a.getRelevanceScore()))
            .limit(maxResults)
            .toList();
    }
    
    /**
     * Obtém estatísticas do agente
     */
    public String getAgentStats() {
        return String.format(
            "NewsCuratorAgent Stats:\n" +
            "- Ferramentas ativas: 4\n" +
            "- Categorias suportadas: %s\n" +
            "- Máximo de resultados: %d\n" +
            "- Idioma: %s",
            String.join(", ", configManager.getSupportedCategories()),
            configManager.getMaxSearchResults(),
            configManager.getSearchLanguage()
        );
    }
}

