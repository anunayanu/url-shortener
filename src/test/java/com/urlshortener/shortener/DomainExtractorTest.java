package com.urlshortener.shortener;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomainExtractorTest {

    @Test
    void extractDomain_stripsWww() {
        assertEquals("youtube.com", DomainExtractor.extractDomain("https://www.youtube.com/watch?v=1"));
        assertEquals("youtube.com", DomainExtractor.extractDomain("https://youtube.com/watch?v=1"));
        assertEquals("wikipedia.org", DomainExtractor.extractDomain("https://www.wikipedia.org/wiki/Go"));
        assertEquals("stackoverflow.com", DomainExtractor.extractDomain("https://stackoverflow.com/questions/1"));
        assertEquals("udemy.com", DomainExtractor.extractDomain("https://udemy.com/course/x"));
    }

    @Test
    void extractDomain_invalidUrlReturnsEmpty() {
        String result = DomainExtractor.extractDomain("://bad");
        assertTrue(result == null || result.isEmpty());
    }
}
