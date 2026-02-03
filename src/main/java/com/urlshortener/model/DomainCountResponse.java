package com.urlshortener.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DomainCountResponse(
    @JsonProperty("domain") String domain,
    @JsonProperty("count") int count
) {}
