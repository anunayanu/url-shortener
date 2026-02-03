package com.urlshortener.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ShortenRequest(
    @JsonProperty("url") String url
) {}
