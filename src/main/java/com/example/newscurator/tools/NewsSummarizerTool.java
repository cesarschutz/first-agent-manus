package com.example.newscurator.tools;

import com.example.newscurator.models.NewsArticle;
import com.example.newscurator.utils.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * Ferramenta para gerar resumos de notícias usando IA
 * 
 * NOTA: Esta é uma implementação simulada para demonstração.
 * Em um ambiente real, você integraria com:
 * - Google Gemini API
 * - OpenAI GPT API
 * - Outras APIs de processamento de linguagem natural
 */
public class NewsSummarizerTool {
    
    private static final Logger logger = LoggerFactory.getLogger(NewsSummarizerTool.class);
    private final ConfigManager configManager;
    private final Random random;
    
    // Templates de resumo para diferentes categorias
    private static final String[] TECH_SUMMARIES = {
        "Avanços tecnológicos prometem revolucionar o setor com novas funcionalidades e maior eficiência.",
        "Inovação disruptiva traz soluções inteligentes para problemas complexos do mercado atual.",
        "Desenvolvimento de nova tecnologia oferece oportunidades de crescimento e transformação digital.",
        "Implementação de IA e automação acelera processos e melhora experiência do usuário.",
        "Startup brasileira desenvolve solução inovadora com potencial de expansão internacional."
    };
    
    private static final String[] POLITICS_SUMMARIES = {
        "Nova legislação busca modernizar marcos regulatórios e fortalecer instituições democráticas.",
        "Governo anuncia medidas para impulsionar economia e gerar empregos em setores estratégicos.",
        "Reforma estrutural visa simplificar processos e aumentar eficiência do setor público.",
        "Investimentos em infraestrutura prometem melhorar qualidade de vida da população.",
        "Política pública inovadora foca em sustentabilidade e desenvolvimento social."
    };
    
    public NewsSummarizerTool(ConfigManager configManager) {
        this.configManager = configManager;
        this.random = new Random();
        logger.info("NewsSummarizerTool inicializada");
    }
    
    /**
     * Gera resumos para uma lista de artigos
     * 
     * @param articles Lista de artigos para resumir
     * @return Lista de artigos com resumos gerados
     */
    public List<NewsArticle> summarizeArticles(List<NewsArticle> articles) {
        logger.info("Gerando resumos para {} artigos", articles.size());
        
        List<NewsArticle> summarizedArticles = new ArrayList<>();
        
        for (NewsArticle article : articles) {
            try {
                String summary = generateSummary(article);
                article.setSummary(summary);
                summarizedArticles.add(article);
                
                logger.debug("Resumo gerado para artigo: {}", article.getTitle());
                
            } catch (Exception e) {
                logger.error("Erro ao gerar resumo para artigo: {}", article.getTitle(), e);
                // Adiciona artigo sem resumo em caso de erro
                article.setSummary("Resumo não disponível");
                summarizedArticles.add(article);
            }
        }
        
        logger.info("Resumos gerados com sucesso para {} artigos", summarizedArticles.size());
        return summarizedArticles;
    }
    
    /**
     * Gera resumo para um artigo específico
     * 
     * @param article Artigo para resumir
     * @return Resumo gerado
     */
    public String generateSummary(NewsArticle article) {
        logger.debug("Gerando resumo para: {}", article.getTitle());
        
        try {
            // Simula chamada para API de IA para gerar resumo
            String summary = simulateAISummary(article);
            
            // Garante que o resumo não excede o tamanho máximo
            int maxLength = configManager.getMaxSummaryLength();
            if (summary.length() > maxLength) {
                summary = summary.substring(0, maxLength - 3) + "...";
            }
            
            return summary;
            
        } catch (Exception e) {
            logger.error("Erro ao gerar resumo", e);
            return "Erro ao gerar resumo: " + e.getMessage();
        }
    }
    
    /**
     * Gera resumo personalizado com instruções específicas
     */
    public String generateCustomSummary(NewsArticle article, String instructions) {
        logger.debug("Gerando resumo personalizado para: {} com instruções: {}", 
                    article.getTitle(), instructions);
        
        // Em uma implementação real, as instruções seriam passadas para a API de IA
        String baseSummary = generateSummary(article);
        
        // Adiciona contexto baseado nas instruções
        if (instructions.toLowerCase().contains("técnico")) {
            baseSummary = "Análise técnica: " + baseSummary;
        } else if (instructions.toLowerCase().contains("simples")) {
            baseSummary = "Em termos simples: " + baseSummary;
        }
        
        return baseSummary;
    }
    
    /**
     * Simula geração de resumo usando IA (implementação para demonstração)
     * Em um ambiente real, aqui seria feita a chamada para uma API de IA
     */
    private String simulateAISummary(NewsArticle article) {
        // Simula processamento baseado na categoria do artigo
        String category = article.getCategory();
        String[] summaryTemplates;
        
        if ("tecnologia".equalsIgnoreCase(category)) {
            summaryTemplates = TECH_SUMMARIES;
        } else if ("política".equalsIgnoreCase(category)) {
            summaryTemplates = POLITICS_SUMMARIES;
        } else {
            // Resumo genérico
            summaryTemplates = new String[]{
                "Desenvolvimento importante no setor traz novas perspectivas para o mercado brasileiro.",
                "Análise especializada revela tendências significativas e oportunidades de crescimento.",
                "Estudo detalhado apresenta dados relevantes sobre impactos econômicos e sociais.",
                "Especialistas destacam importância do tema para o desenvolvimento nacional.",
                "Pesquisa recente mostra evolução positiva em indicadores-chave do setor."
            };
        }
        
        // Seleciona template aleatório e personaliza
        String template = summaryTemplates[random.nextInt(summaryTemplates.length)];
        
        // Adiciona contexto específico do artigo
        String title = article.getTitle();
        if (title.length() > 50) {
            String titleContext = title.substring(0, 47) + "...";
            template = "Sobre '" + titleContext + "': " + template;
        }
        
        return template;
    }
    
    /**
     * Analisa sentimento do artigo (funcionalidade adicional)
     */
    public String analyzeSentiment(NewsArticle article) {
        // Simula análise de sentimento
        String title = article.getTitle().toLowerCase();
        
        if (title.contains("crescimento") || title.contains("sucesso") || 
            title.contains("inovação") || title.contains("melhoria")) {
            return "POSITIVE";
        } else if (title.contains("crise") || title.contains("problema") || 
                   title.contains("queda") || title.contains("falha")) {
            return "NEGATIVE";
        } else {
            return "NEUTRAL";
        }
    }
}

