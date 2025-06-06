package com.example.newscurator.tools;

import com.example.newscurator.models.NewsArticle;
import com.example.newscurator.utils.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;

/**
 * Ferramenta para categorizar notícias automaticamente
 */
public class NewsCategorizerTool {
    
    private static final Logger logger = LoggerFactory.getLogger(NewsCategorizerTool.class);
    private final ConfigManager configManager;
    
    public NewsCategorizerTool(ConfigManager configManager) {
        this.configManager = configManager;
        logger.info("NewsCategorizerTool inicializada");
    }
    
    /**
     * Categoriza uma lista de artigos
     * 
     * @param articles Lista de artigos para categorizar
     * @return Lista de artigos com categorias atribuídas
     */
    public List<NewsArticle> categorizeArticles(List<NewsArticle> articles) {
        logger.info("Categorizando {} artigos", articles.size());
        
        List<NewsArticle> categorizedArticles = new ArrayList<>();
        
        for (NewsArticle article : articles) {
            try {
                String category = categorizeArticle(article);
                article.setCategory(category);
                categorizedArticles.add(article);
                
                logger.debug("Artigo '{}' categorizado como: {}", article.getTitle(), category);
                
            } catch (Exception e) {
                logger.error("Erro ao categorizar artigo: {}", article.getTitle(), e);
                // Define categoria padrão em caso de erro
                article.setCategory("geral");
                categorizedArticles.add(article);
            }
        }
        
        logger.info("Categorização concluída para {} artigos", categorizedArticles.size());
        return categorizedArticles;
    }
    
    /**
     * Categoriza um artigo individual baseado no título e palavras-chave
     * 
     * @param article Artigo para categorizar
     * @return Categoria identificada
     */
    public String categorizeArticle(NewsArticle article) {
        String title = article.getTitle().toLowerCase();
        List<String> keywords = article.getKeywords();
        
        // Verifica palavras-chave no título
        if (containsKeywords(title, getTechnologyKeywords())) {
            return "tecnologia";
        } else if (containsKeywords(title, getPoliticsKeywords())) {
            return "política";
        } else if (containsKeywords(title, getEconomyKeywords())) {
            return "economia";
        } else if (containsKeywords(title, getSportsKeywords())) {
            return "esportes";
        } else if (containsKeywords(title, getHealthKeywords())) {
            return "saúde";
        } else if (containsKeywords(title, getScienceKeywords())) {
            return "ciência";
        }
        
        // Verifica palavras-chave do artigo se disponíveis
        if (keywords != null && !keywords.isEmpty()) {
            for (String keyword : keywords) {
                String keywordLower = keyword.toLowerCase();
                
                if (getTechnologyKeywords().contains(keywordLower)) {
                    return "tecnologia";
                } else if (getPoliticsKeywords().contains(keywordLower)) {
                    return "política";
                } else if (getEconomyKeywords().contains(keywordLower)) {
                    return "economia";
                }
            }
        }
        
        // Categoria padrão
        return "geral";
    }
    
    /**
     * Verifica se o texto contém alguma das palavras-chave
     */
    private boolean containsKeywords(String text, List<String> keywords) {
        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Palavras-chave para categoria Tecnologia
     */
    private List<String> getTechnologyKeywords() {
        return List.of(
            "tecnologia", "tech", "inteligência artificial", "ia", "machine learning",
            "blockchain", "criptomoeda", "bitcoin", "startup", "app", "aplicativo",
            "software", "hardware", "internet", "digital", "inovação", "5g",
            "android", "ios", "google", "apple", "microsoft", "meta", "tesla",
            "robô", "automação", "dados", "cloud", "nuvem", "cybersecurity"
        );
    }
    
    /**
     * Palavras-chave para categoria Política
     */
    private List<String> getPoliticsKeywords() {
        return List.of(
            "política", "governo", "presidente", "ministro", "congresso", "senado",
            "câmara", "deputado", "senador", "eleição", "voto", "partido",
            "reforma", "lei", "projeto", "pec", "medida provisória", "stf",
            "supremo", "justiça", "tribunal", "democracia", "constituição"
        );
    }
    
    /**
     * Palavras-chave para categoria Economia
     */
    private List<String> getEconomyKeywords() {
        return List.of(
            "economia", "econômico", "financeiro", "mercado", "bolsa", "ação",
            "investimento", "pib", "inflação", "juros", "selic", "dólar",
            "real", "moeda", "banco", "crédito", "emprego", "desemprego",
            "renda", "salário", "imposto", "tributário", "fiscal", "orçamento"
        );
    }
    
    /**
     * Palavras-chave para categoria Esportes
     */
    private List<String> getSportsKeywords() {
        return List.of(
            "esporte", "futebol", "basquete", "vôlei", "tênis", "natação",
            "atletismo", "olimpíadas", "copa", "mundial", "campeonato",
            "jogador", "atleta", "time", "clube", "técnico", "gol",
            "vitória", "derrota", "jogo", "partida", "competição"
        );
    }
    
    /**
     * Palavras-chave para categoria Saúde
     */
    private List<String> getHealthKeywords() {
        return List.of(
            "saúde", "medicina", "médico", "hospital", "tratamento", "doença",
            "vacina", "remédio", "medicamento", "sus", "anvisa", "covid",
            "pandemia", "vírus", "bactéria", "pesquisa médica", "terapia",
            "cirurgia", "diagnóstico", "prevenção", "sintoma"
        );
    }
    
    /**
     * Palavras-chave para categoria Ciência
     */
    private List<String> getScienceKeywords() {
        return List.of(
            "ciência", "pesquisa", "estudo", "descoberta", "cientista",
            "universidade", "laboratório", "experimento", "nasa", "espaço",
            "astronomia", "física", "química", "biologia", "genética",
            "dna", "clima", "meio ambiente", "sustentabilidade", "energia"
        );
    }
    
    /**
     * Obtém estatísticas de categorização
     */
    public String getCategorizationStats(List<NewsArticle> articles) {
        if (articles.isEmpty()) {
            return "Nenhum artigo para analisar";
        }
        
        // Conta artigos por categoria
        var categoryCount = articles.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                NewsArticle::getCategory,
                java.util.stream.Collectors.counting()
            ));
        
        StringBuilder stats = new StringBuilder("Estatísticas de Categorização:\n");
        categoryCount.forEach((category, count) -> 
            stats.append(String.format("- %s: %d artigos\n", category, count))
        );
        
        return stats.toString();
    }
}

