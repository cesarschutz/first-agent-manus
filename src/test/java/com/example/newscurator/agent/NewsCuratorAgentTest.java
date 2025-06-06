package com.example.newscurator.agent;

import com.example.newscurator.models.NewsArticle;
import com.example.newscurator.models.NewsReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

/**
 * Testes unitários para o NewsCuratorAgent
 */
public class NewsCuratorAgentTest {
    
    private NewsCuratorAgent agent;
    
    @BeforeEach
    void setUp() {
        agent = new NewsCuratorAgent();
    }
    
    @Test
    void testCurateNewsWithSingleTopic() {
        // Arrange
        String topic = "tecnologia";
        
        // Act
        NewsReport report = agent.curateNews(topic);
        
        // Assert
        assertNotNull(report);
        assertNotNull(report.getTitle());
        assertTrue(report.getTitle().contains(topic));
        assertTrue(report.getTotalArticles() > 0);
        assertEquals(1, report.getTopics().size());
        assertEquals(topic, report.getTopics().get(0));
    }
    
    @Test
    void testCurateNewsWithMultipleTopics() {
        // Arrange
        List<String> topics = Arrays.asList("tecnologia", "inovação", "startups");
        
        // Act
        NewsReport report = agent.curateNews(topics);
        
        // Assert
        assertNotNull(report);
        assertEquals(3, report.getTopics().size());
        assertTrue(report.getTotalArticles() > 0);
        assertNotNull(report.getSummary());
    }
    
    @Test
    void testCurateNewsWithCustomParameters() {
        // Arrange
        List<String> topics = Arrays.asList("inteligência artificial");
        int maxArticles = 3;
        double minRelevanceScore = 0.5;
        
        // Act
        NewsReport report = agent.curateNews(topics, maxArticles, minRelevanceScore);
        
        // Assert
        assertNotNull(report);
        assertTrue(report.getTotalArticles() <= maxArticles);
        
        // Verifica se todos os artigos têm score >= minRelevanceScore
        for (NewsArticle article : report.getArticles()) {
            assertTrue(article.getRelevanceScore() >= minRelevanceScore);
        }
    }
    
    @Test
    void testAgentStats() {
        // Act
        String stats = agent.getAgentStats();
        
        // Assert
        assertNotNull(stats);
        assertTrue(stats.contains("NewsCuratorAgent Stats"));
        assertTrue(stats.contains("Ferramentas ativas"));
    }
}

