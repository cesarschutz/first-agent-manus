package com.example.newscurator.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Modelo que representa uma notícia processada pelo agente
 */
public class NewsArticle {
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("summary")
    private String summary;
    
    @JsonProperty("url")
    private String url;
    
    @JsonProperty("source")
    private String source;
    
    @JsonProperty("category")
    private String category;
    
    @JsonProperty("publishedAt")
    private LocalDateTime publishedAt;
    
    @JsonProperty("relevanceScore")
    private double relevanceScore;
    
    @JsonProperty("keywords")
    private List<String> keywords;
    
    @JsonProperty("sentiment")
    private String sentiment; // POSITIVE, NEGATIVE, NEUTRAL
    
    // Construtores
    public NewsArticle() {}
    
    public NewsArticle(String title, String summary, String url, String source) {
        this.title = title;
        this.summary = summary;
        this.url = url;
        this.source = source;
        this.publishedAt = LocalDateTime.now();
        this.relevanceScore = 0.0;
    }
    
    // Getters e Setters
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getSource() {
        return source;
    }
    
    public void setSource(String source) {
        this.source = source;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }
    
    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }
    
    public double getRelevanceScore() {
        return relevanceScore;
    }
    
    public void setRelevanceScore(double relevanceScore) {
        this.relevanceScore = relevanceScore;
    }
    
    public List<String> getKeywords() {
        return keywords;
    }
    
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
    
    public String getSentiment() {
        return sentiment;
    }
    
    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }
    
    @Override
    public String toString() {
        return String.format("NewsArticle{title='%s', source='%s', category='%s', relevanceScore=%.2f}", 
                           title, source, category, relevanceScore);
    }
}

