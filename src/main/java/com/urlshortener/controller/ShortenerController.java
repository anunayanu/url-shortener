package com.urlshortener.controller;

import com.urlshortener.exception.NotFoundException;
import com.urlshortener.model.MetricsDomainsResponse;
import com.urlshortener.model.ShortenRequest;
import com.urlshortener.model.ShortenResponse;
import com.urlshortener.service.ShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class ShortenerController {

    private final ShortenerService shortenerService;

    public ShortenerController(ShortenerService shortenerService) {
        this.shortenerService = shortenerService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortenResponse> shorten(@RequestBody ShortenRequest request) {
        ShortenerService.ShortenResult result = shortenerService.shorten(request.url());
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ShortenResponse(result.shortUrl(), result.original()));
    }

}
