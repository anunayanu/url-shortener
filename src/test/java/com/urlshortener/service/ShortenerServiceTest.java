package com.urlshortener.service;

import com.urlshortener.exception.InvalidUrlException;
import com.urlshortener.store.InMemoryStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShortenerServiceTest {

    private InMemoryStore store;
    private ShortenerService service;

    @BeforeEach
    void setUp() {
        store = new InMemoryStore();
        service = new ShortenerService(store, "http://localhost:8080");
    }

    @Test
    void shorten_sameUrlReturnsSameShortUrl() {
        String url = "https://www.youtube.com/watch?v=example";
        ShortenerService.ShortenResult r1 = service.shorten(url);
        ShortenerService.ShortenResult r2 = service.shorten(url);
        assertEquals(r1.shortUrl(), r2.shortUrl());
        assertEquals(r1.shortCode(), r2.shortCode());
    }

    @Test
    void shorten_invalidUrlThrows() {
        assertThrows(InvalidUrlException.class, () -> service.shorten("not-a-url"));
        assertThrows(InvalidUrlException.class, () -> service.shorten(""));
        assertThrows(InvalidUrlException.class, () -> service.shorten(null));
    }

    @Test
    void resolve_returnsOriginalUrl() {
        store.save("abc", "https://example.com", "example.com");
        assertEquals("https://example.com", service.resolve("abc"));
        assertNull(service.resolve("none"));
    }

    @Test
    void topDomains_returnsTopThree() {
        for (int i = 0; i < 6; i++) {
            store.save("u" + i, "https://udemy.com/course/" + i, "udemy.com");
        }
        for (int i = 0; i < 4; i++) {
            store.save("y" + i, "https://youtube.com/watch?v=" + i, "youtube.com");
        }
        for (int i = 0; i < 2; i++) {
            store.save("w" + i, "https://wikipedia.org/wiki/" + i, "wikipedia.org");
        }
        store.save("s1", "https://stackoverflow.com/q/1", "stackoverflow.com");

        var top = service.topDomains();
        assertEquals(3, top.size());
        assertEquals("udemy.com", top.get(0).domain());
        assertEquals(6, top.get(0).count());
        assertEquals("youtube.com", top.get(1).domain());
        assertEquals(4, top.get(1).count());
        assertEquals("wikipedia.org", top.get(2).domain());
        assertEquals(2, top.get(2).count());
    }
}
