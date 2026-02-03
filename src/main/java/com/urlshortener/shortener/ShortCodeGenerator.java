package com.urlshortener.shortener;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Generates a short code for a long URL. Same URL always yields the same code (idempotent).
 * Uses first 8 chars of base64url(sha256(normalizedUrl)).
 */
public final class ShortCodeGenerator {

    private static final int CODE_LENGTH = 8;
    private static final Base64.Encoder BASE64_URL = Base64.getUrlEncoder().withoutPadding();

    private ShortCodeGenerator() {}

    public static String generateCode(String longUrl) {
        String normalized = normalizeUrl(longUrl);
        byte[] hash = sha256(normalized.getBytes(StandardCharsets.UTF_8));
        String encoded = BASE64_URL.encodeToString(hash);
        if (encoded.length() > CODE_LENGTH) {
            encoded = encoded.substring(0, CODE_LENGTH);
        }
        return encoded;
    }

    private static String normalizeUrl(String raw) {
        try {
            URI uri = URI.create(raw);
            String scheme = uri.getScheme();
            String host = uri.getHost();
            String path = uri.getRawPath();
            String query = uri.getRawQuery();
            if (path == null) path = "";
            StringBuilder sb = new StringBuilder();
            if (scheme != null) sb.append(scheme).append("://");
            if (host != null) sb.append(host);
            sb.append(path);
            if (query != null && !query.isEmpty()) sb.append("?").append(query);
            return sb.toString();
        } catch (Exception e) {
            return raw;
        }
    }

    private static byte[] sha256(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
}
