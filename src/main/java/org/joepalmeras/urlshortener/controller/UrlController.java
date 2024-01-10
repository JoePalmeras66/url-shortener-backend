package org.joepalmeras.urlshortener.controller;

import org.apache.commons.validator.routines.UrlValidator;
import org.joepalmeras.urlshortener.dto.CustomShortUrlRequest;
import org.joepalmeras.urlshortener.dto.ShortUrlRequest;
import org.joepalmeras.urlshortener.dto.ShortUrlResponse;
import org.joepalmeras.urlshortener.service.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlShortenerService service;

    @PostMapping("/createUrl")
    public ResponseEntity<ShortUrlResponse> createUrl(
            @RequestBody ShortUrlRequest request) {
        UrlValidator urlValidator = new UrlValidator();
        if(urlValidator.isValid(request.getUrl())) {
            ShortUrlResponse response = service.createShortUrl(request);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/shortUrl")
    public RedirectView redirect(@RequestBody ShortUrlRequest request) {
        return service.getFullUrl(request.getUrl());
    }

    @PostMapping("/createCustomUrl")
    public ResponseEntity<ShortUrlResponse> customUrl(
            @RequestBody CustomShortUrlRequest request) {
        String customShortUrl = request.getCustomShortUrl();
        if(customShortUrl == null)
            return ResponseEntity.ofNullable(null);
        if(request.getUrl() == null)
            return ResponseEntity.ofNullable(null);

        ShortUrlResponse response = service.createCustomShortUrl(request);
        return ResponseEntity.ok(response);
    }


}
