import com.google.common.hash.Hashing; // Common in industry (Guava)
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class ShortCodeGenerator {

    private static final Base64.Encoder BASE64_URL = Base64.getUrlEncoder().withoutPadding();

    private ShortCodeGenerator() {}

    public static String generateCode(String longUrl) {
        // 1. Better Normalization
        String normalized = normalizeUrl(longUrl);

        // 2. Faster Hashing (MurmurHash3_32 gives a 32-bit hash)
        // Using the 128-bit version for a better balance of speed and collision resistance
        byte[] hash = Hashing.murmur3_128()
                .hashString(normalized, StandardCharsets.UTF_8)
                .asBytes();

        // 3. Encode and Truncate
        String encoded = BASE64_URL.encodeToString(hash);
        return encoded.substring(0, 8);
    }

    private static String normalizeUrl(String raw) {
        if (raw == null) return "";
        try {
            URI uri = new URI(raw.trim()).normalize();
            String scheme = uri.getScheme() != null ? uri.getScheme().toLowerCase() : "http";
            String host = uri.getHost() != null ? uri.getHost().toLowerCase() : "";
            String path = uri.getRawPath() != null ? uri.getRawPath() : "";
            String query = uri.getRawQuery() != null ? "?" + uri.getRawQuery() : "";

            // Reconstruct without user-info or fragments for consistency
            return scheme + "://" + host + path + query;
        } catch (Exception e) {
            return raw.toLowerCase().trim();
        }
    }
}