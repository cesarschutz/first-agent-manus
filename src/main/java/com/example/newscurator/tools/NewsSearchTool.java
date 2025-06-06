package com.example.newscurator.tools;

import com.example.newscurator.models.NewsArticle;
import com.example.newscurator.utils.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Ferramenta para buscar notícias usando APIs de busca
 * 
 * NOTA: Esta é uma implementação simulada para demonstração.
 * Em um ambiente real, você integraria com APIs como:
 * - Google News API
 * - NewsAPI.org
 * - Bing News Search API
 */
public class NewsSearchTool {
    
    private static final Logger logger = LoggerFactory.getLogger(NewsSearchTool.class);
    private final ConfigManager configManager;
    private final Random random;
    
    // Dados simulados para demonstração
    private static final String[] SAMPLE_SOURCES = {
        "G1", "Folha de S.Paulo", "O Globo", "UOL", "R7", "BBC Brasil", 
        "CNN Brasil", "Estadão", "Valor Econômico", "TechCrunch Brasil"
    };
    
    private static final String[] SAMPLE_TITLES_TECH = {
        "Nova atualização do Android traz recursos de IA avançados",
        "Startup brasileira desenvolve solução inovadora para IoT",
        "Inteligência artificial revoluciona setor de saúde no Brasil",
        "Criptomoedas ganham regulamentação mais clara no país",
        "5G chega a mais cidades brasileiras neste mês"
    };
    
    private static final String[] SAMPLE_TITLES_POLITICS = {
        "Congresso aprova nova lei de proteção de dados",
        "Presidente anuncia investimentos em infraestrutura",
        "Reforma tributária avança no Senado Federal",
        "Ministério da Educação lança programa de digitalização",
        "Governo federal apresenta plano de sustentabilidade"
    };
    
    public NewsSearchTool(ConfigManager configManager) {
        this.configManager = configManager;
        this.random = new Random();
        logger.info("NewsSearchTool inicializada");
    }
    
    /**
     * Busca notícias sobre um tópico específico
     * 
     * @param topic Tópico para buscar notícias
     * @return Lista de artigos encontrados
     */
    public List<NewsArticle> searchNews(String topic) {
        logger.info("Buscando notícias para o tópico: {}", topic);
        
        try {
            // Simula chamada para API de notícias
            List<NewsArticle> articles = simulateNewsSearch(topic);
            
            logger.info("Encontrados {} artigos para o tópico '{}'", articles.size(), topic);
            return articles;
            
        } catch (Exception e) {
            logger.error("Erro ao buscar notícias para o tópico: {}", topic, e);
            return new ArrayList<>();
        }
    }
    
    /**
     * Busca notícias com filtros específicos
     */
    public List<NewsArticle> searchNews(String topic, String category, int maxResults) {
        logger.info("Busca filtrada - Tópico: {}, Categoria: {}, Max: {}", topic, category, maxResults);
        
        List<NewsArticle> articles = searchNews(topic);
        
        // Filtra por categoria se especificada
        if (category != null && !category.isEmpty()) {
            articles = articles.stream()
                .filter(article -> category.equalsIgnoreCase(article.getCategory()))
                .toList();
        }
        
        // Limita o número de resultados
        if (articles.size() > maxResults) {
            articles = articles.subList(0, maxResults);
        }
        
        return articles;
    }
    
    /**
     * Simula a busca de notícias (implementação para demonstração)
     * Em um ambiente real, aqui seria feita a chamada para uma API real
     */
    private List<NewsArticle> simulateNewsSearch(String topic) {
        List<NewsArticle> articles = new ArrayList<>();
        int numArticles = Math.min(configManager.getMaxSearchResults(), 5 + random.nextInt(6));
        
        String[] titles = getTitlesForTopic(topic);
        
        for (int i = 0; i < numArticles; i++) {
            NewsArticle article = new NewsArticle();
            
            // Título baseado no tópico
            article.setTitle(titles[random.nextInt(titles.length)]);
            
            // URL simulada
            article.setUrl("https://example-news.com/article-" + (i + 1));
            
            // Fonte aleatória
            article.setSource(SAMPLE_SOURCES[random.nextInt(SAMPLE_SOURCES.length)]);
            
            // Score de relevância baseado no tópico
            double relevanceScore = calculateRelevanceScore(topic, article.getTitle());
            article.setRelevanceScore(relevanceScore);
            
            // Palavras-chave baseadas no tópico
            article.setKeywords(generateKeywords(topic));
            
            articles.add(article);
        }
        
        return articles;
    }
    
    /**
     * Obtém títulos de exemplo baseados no tópico
     */
    private String[] getTitlesForTopic(String topic) {
        String topicLower = topic.toLowerCase();
        
        if (topicLower.contains("tecnologia") || topicLower.contains("tech") || 
            topicLower.contains("ia") || topicLower.contains("inteligência")) {
            return SAMPLE_TITLES_TECH;
        } else if (topicLower.contains("política") || topicLower.contains("governo") || 
                   topicLower.contains("congresso")) {
            return SAMPLE_TITLES_POLITICS;
        } else {
            // Títulos genéricos
            return new String[]{
                "Novidades sobre " + topic + " movimentam o mercado",
                "Especialistas analisam tendências em " + topic,
                "Setor de " + topic + " apresenta crescimento significativo",
                "Inovações em " + topic + " prometem transformar indústria",
                "Estudo revela impacto de " + topic + " na economia"
            };
        }
    }
    
    /**
     * Calcula score de relevância baseado na correspondência entre tópico e título
     */
    private double calculateRelevanceScore(String topic, String title) {
        String topicLower = topic.toLowerCase();
        String titleLower = title.toLowerCase();
        
        // Score base
        double score = 0.5;
        
        // Aumenta score se o tópico aparece no título
        if (titleLower.contains(topicLower)) {
            score += 0.3;
        }
        
        // Adiciona variação aleatória
        score += (random.nextDouble() - 0.5) * 0.4;
        
        // Garante que está entre 0 e 1
        return Math.max(0.0, Math.min(1.0, score));
    }
    
    /**
     * Gera palavras-chave baseadas no tópico
     */
    private List<String> generateKeywords(String topic) {
        List<String> keywords = new ArrayList<>();
        keywords.add(topic);
        
        String topicLower = topic.toLowerCase();
        if (topicLower.contains("tecnologia")) {
            keywords.addAll(Arrays.asList("inovação", "digital", "startup", "software"));
        } else if (topicLower.contains("política")) {
            keywords.addAll(Arrays.asList("governo", "lei", "congresso", "reforma"));
        } else {
            keywords.addAll(Arrays.asList("mercado", "economia", "brasil", "setor"));
        }
        
        return keywords;
    }
}

