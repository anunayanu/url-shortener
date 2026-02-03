package com.urlshortener.shortener;

import java.net.URI;

/**
 * Extracts the host from a URL, normalized by stripping "www." prefix.
 * e.g. "https://www.youtube.com/watch?v=1" -> "youtube.com"
 */
public final class DomainExtractor {

    private DomainExtractor() {}

    public static String extractDomain(String rawUrl) {
        try {
            URI uri = URI.create(rawUrl);
            String host = uri.getHost();
            if (host == null || host.isEmpty()) {
                return "";
            }
            return normalizeHost(host);
        } catch (Exception e) {
            return "";
        }
    }

    private static String normalizeHost(String host) {
        String lower = host.toLowerCase();
        if (lower.startsWith("www.")) {
            return lower.substring(4);
        }
        return lower;
    }
}
