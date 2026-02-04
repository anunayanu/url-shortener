package com.urlshortener.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryStoreTest {

    private InMemoryStore store;

    @BeforeEach
    void setUp() {
        store = new InMemoryStore();
    }

    @Test
    void saveAndGetByShortCode() {
        store.save("abc", "https://youtube.com", "youtube.com");
        assertEquals("https://youtube.com", store.getByShortCode("abc"));
    }

    @Test
    void getByUrlReturnsShortCode() {
        store.save("abc", "https://youtube.com", "youtube.com");
        assertEquals("abc", store.getByUrl("https://youtube.com"));
    }

    @Test
    void getByShortCode_notFoundReturnsNull() {
        assertNull(store.getByShortCode("none"));
    }

    @Test
    void domainCounts() {
        store.save("a", "https://youtube.com/a", "youtube.com");
        store.save("b", "https://youtube.com/b", "youtube.com");
        store.save("c", "https://wikipedia.org/x", "wikipedia.org");
        Map<String, Integer> counts = store.getDomainCounts();
        assertEquals(2, counts.get("youtube.com"));
        assertEquals(1, counts.get("wikipedia.org"));
    }
}
