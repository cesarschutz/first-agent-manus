package com.example.newscurator.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Gerenciador de configurações para o agente curador de notícias
 */
public class ConfigManager {
    
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    private final Properties properties;
    
    public ConfigManager() {
        properties = new Properties();
        loadProperties();
    }
    
    /**
     * Carrega as configurações do arquivo application.properties
     */
    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                logger.error("Não foi possível encontrar o arquivo application.properties");
                throw new RuntimeException("Arquivo de configuração não encontrado");
            }
            
            properties.load(input);
            logger.info("Configurações carregadas com sucesso");
            
        } catch (IOException e) {
            logger.error("Erro ao carregar configurações", e);
            throw new RuntimeException("Falha ao carregar configurações: " + e.getMessage(), e);
        }
    }
    
    /**
     * Obtém a chave de API do Google
     */
    public String getGoogleApiKey() {
        return properties.getProperty("google.api.key");
    }
    
    /**
     * Obtém o ID do projeto Google
     */
    public String getGoogleProjectId() {
        return properties.getProperty("google.project.id");
    }
    
    /**
     * Obtém o número máximo de resultados de busca
     */
    public int getMaxSearchResults() {
        return Integer.parseInt(properties.getProperty("news.search.max.results", "10"));
    }
    
    /**
     * Define o número máximo de resultados de busca
     */
    public void setMaxSearchResults(int maxResults) {
        properties.setProperty("news.search.max.results", String.valueOf(maxResults));
    }
    
    /**
     * Obtém o idioma de busca
     */
    public String getSearchLanguage() {
        return properties.getProperty("news.search.language", "pt");
    }
    
    /**
     * Obtém o país de busca
     */
    public String getSearchCountry() {
        return properties.getProperty("news.search.country", "BR");
    }
    
    /**
     * Obtém o tamanho máximo do resumo
     */
    public int getMaxSummaryLength() {
        return Integer.parseInt(properties.getProperty("news.summary.max.length", "200"));
    }
    
    /**
     * Obtém as categorias suportadas
     */
    public List<String> getSupportedCategories() {
        String categoriesStr = properties.getProperty("news.categories", 
                                                    "tecnologia,política,economia,esportes,saúde,ciência");
        return Arrays.asList(categoriesStr.split(","));
    }
    
    /**
     * Verifica se o cache está habilitado
     */
    public boolean isCacheEnabled() {
        return Boolean.parseBoolean(properties.getProperty("cache.enabled", "true"));
    }
    
    /**
     * Obtém a duração do cache em minutos
     */
    public int getCacheDurationMinutes() {
        return Integer.parseInt(properties.getProperty("cache.duration.minutes", "30"));
    }
    
    /**
     * Obtém o nível de logging
     */
    public String getLoggingLevel() {
        return properties.getProperty("logging.level", "INFO");
    }
    
    /**
     * Obtém o caminho do arquivo de log
     */
    public String getLogFilePath() {
        return properties.getProperty("logging.file.path", "logs/news-curator.log");
    }
    
    /**
     * Obtém uma propriedade genérica
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Define uma propriedade genérica
     */
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
}

