package com.urlshortener.service;

import com.urlshortener.exception.InvalidUrlException;
import com.urlshortener.model.DomainCountResponse;
import com.urlshortener.shortener.DomainExtractor;
import com.urlshortener.shortener.ShortCodeGenerator;
import com.urlshortener.store.InMemoryStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShortenerService {

    private static final int TOP_DOMAINS_LIMIT = 3;

    private final InMemoryStore store;
    private final String baseUrl;

    public ShortenerService(InMemoryStore store,
                            @Value("${urlshortener.base-url:http://localhost:8080}") String baseUrl) {
        this.store = store;
        this.baseUrl = baseUrl;
    }

    public ShortenResult shorten(String longUrl) {
        if (longUrl == null || longUrl.isBlank()) {
            throw new InvalidUrlException("URL is required");
        }
        URI uri;
        try {
            uri = URI.create(longUrl);
        } catch (Exception e) {
            throw new InvalidUrlException("Invalid URL");
        }
        String scheme = uri.getScheme();
        if (scheme == null || (!scheme.equalsIgnoreCase("http") && !scheme.equalsIgnoreCase("https"))) {
            throw new InvalidUrlException("Invalid URL");
        }
        if (uri.getHost() == null || uri.getHost().isBlank()) {
            throw new InvalidUrlException("Invalid URL");
        }

        String existing = store.getByUrl(longUrl);
        if (existing != null) {
            return new ShortenResult(
                baseUrl + "/r/" + existing,
                existing,
                longUrl
            );
        }

        String code = ShortCodeGenerator.generateCode(longUrl);
        String domain = DomainExtractor.extractDomain(longUrl);
        store.save(code, longUrl, domain);
        return new ShortenResult(
            baseUrl + "/r/" + code,
            code,
            longUrl
        );
    }

}
