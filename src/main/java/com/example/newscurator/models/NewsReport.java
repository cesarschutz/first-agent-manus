package com.example.newscurator.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Modelo que representa um relatório de notícias gerado pelo agente
 */
public class NewsReport {
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("generatedAt")
    private LocalDateTime generatedAt;
    
    @JsonProperty("topics")
    private List<String> topics;
    
    @JsonProperty("articles")
    private List<NewsArticle> articles;
    
    @JsonProperty("summary")
    private String summary;
    
    @JsonProperty("categorySummary")
    private Map<String, Integer> categorySummary;
    
    @JsonProperty("totalArticles")
    private int totalArticles;
    
    @JsonProperty("averageRelevanceScore")
    private double averageRelevanceScore;
    
    // Construtores
    public NewsReport() {
        this.generatedAt = LocalDateTime.now();
    }
    
    public NewsReport(String title, List<String> topics) {
        this();
        this.title = title;
        this.topics = topics;
    }
    
    // Getters e Setters
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }
    
    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }
    
    public List<String> getTopics() {
        return topics;
    }
    
    public void setTopics(List<String> topics) {
        this.topics = topics;
    }
    
    public List<NewsArticle> getArticles() {
        return articles;
    }
    
    public void setArticles(List<NewsArticle> articles) {
        this.articles = articles;
        this.totalArticles = articles != null ? articles.size() : 0;
        
        // Calcula score médio de relevância
        if (articles != null && !articles.isEmpty()) {
            this.averageRelevanceScore = articles.stream()
                .mapToDouble(NewsArticle::getRelevanceScore)
                .average()
                .orElse(0.0);
        }
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public Map<String, Integer> getCategorySummary() {
        return categorySummary;
    }
    
    public void setCategorySummary(Map<String, Integer> categorySummary) {
        this.categorySummary = categorySummary;
    }
    
    public int getTotalArticles() {
        return totalArticles;
    }
    
    public void setTotalArticles(int totalArticles) {
        this.totalArticles = totalArticles;
    }
    
    public double getAverageRelevanceScore() {
        return averageRelevanceScore;
    }
    
    public void setAverageRelevanceScore(double averageRelevanceScore) {
        this.averageRelevanceScore = averageRelevanceScore;
    }
    
    @Override
    public String toString() {
        return String.format("NewsReport{title='%s', totalArticles=%d, averageRelevanceScore=%.2f}", 
                           title, totalArticles, averageRelevanceScore);
    }
}

