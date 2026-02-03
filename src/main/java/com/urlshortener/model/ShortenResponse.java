package com.urlshortener.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ShortenResponse(
    @JsonProperty("short_url") String shortUrl,
    @JsonProperty("original") String original
) {}
