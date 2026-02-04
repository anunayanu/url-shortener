package com.urlshortener.shortener;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShortCodeGeneratorTest {

    @Test
    void sameUrlYieldsSameCode() {
        String url = "https://www.youtube.com/watch?v=example";
        String c1 = ShortCodeGenerator.generateCode(url);
        String c2 = ShortCodeGenerator.generateCode(url);
        assertEquals(c1, c2);
        assertNotNull(c1);
        assertFalse(c1.isEmpty());
    }

    @Test
    void differentUrlsYieldDifferentCodes() {
        String c1 = ShortCodeGenerator.generateCode("https://youtube.com/watch?v=1");
        String c2 = ShortCodeGenerator.generateCode("https://wikipedia.org/wiki/Go");
        assertNotEquals(c1, c2);
    }

    @Test
    void codeLengthIsReasonable() {
        String code = ShortCodeGenerator.generateCode("https://example.com");
        assertTrue(code.length() >= 6 && code.length() <= 12);
    }
}
