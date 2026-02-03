package com.urlshortener.store;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory store for shortCode <-> URL mapping and domain counts. Thread-safe.
 */
@Component
public class InMemoryStore {

    private final Map<String, String> shortToUrl = new ConcurrentHashMap<>();
    private final Map<String, String> urlToShort = new ConcurrentHashMap<>();
    private final Map<String, Integer> domainCounts = new ConcurrentHashMap<>();

    public String getByShortCode(String shortCode) {
        return shortToUrl.get(shortCode);
    }

    public String getByUrl(String longUrl) {
        return urlToShort.get(longUrl);
    }

    public void save(String shortCode, String longUrl, String domain) {
        shortToUrl.put(shortCode, longUrl);
        urlToShort.put(longUrl, shortCode);
        if (domain != null && !domain.isEmpty()) {
            domainCounts.merge(domain, 1, Integer::sum);
        }
    }

    public Map<String, Integer> getDomainCounts() {
        return Collections.unmodifiableMap(new HashMap<>(domainCounts));
    }
}
