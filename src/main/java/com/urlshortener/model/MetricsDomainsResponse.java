package com.urlshortener.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record MetricsDomainsResponse(
    @JsonProperty("domains") List<DomainCountResponse> domains
) {}
