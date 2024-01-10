package org.joepalmeras.urlshortener.service;

import org.joepalmeras.urlshortener.dto.CustomShortUrlRequest;
import org.joepalmeras.urlshortener.dto.ShortUrlRequest;
import org.joepalmeras.urlshortener.dto.ShortUrlResponse;
import org.joepalmeras.urlshortener.entity.ShortUrlEntity;
import org.joepalmeras.urlshortener.repository.ShortUrlRepository;
import org.joepalmeras.urlshortener.util.ShortUrlUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {

    private final ShortUrlRepository repository;
    private final ShortUrlUtil util;

    public ShortUrlResponse createShortUrl(ShortUrlRequest request) {
        String fullUrl = request.getUrl();

        ShortUrlEntity existingShortUrl = repository.findByFullUrl(fullUrl);

        if (existingShortUrl != null) {
            return ShortUrlResponse.builder().key(existingShortUrl.getShortUrl()).build();
        } else {
            String newKey = util.generateUniqueKey();
            UriComponents uc = UriComponentsBuilder.fromUriString(fullUrl).build();

            UriComponents uriComponents = UriComponentsBuilder.newInstance()
                    .scheme("http").host(uc.getHost()).path(newKey).build();

            ShortUrlEntity newEntity = ShortUrlEntity.builder()
                    .shortUrl(uriComponents.toString()).fullUrl(fullUrl)
                    .build();
            repository.save(newEntity);
            return ShortUrlResponse.builder().key(uriComponents.toString()).build();
        }
    }

    public ShortUrlResponse createCustomShortUrl(CustomShortUrlRequest request) {
        String fullUrl = request.getUrl();
        ShortUrlEntity existingShortUrl = repository.findByFullUrl(fullUrl);

        if (existingShortUrl != null) {
            return ShortUrlResponse.builder().key(existingShortUrl.getShortUrl()).build();
        } else {
            String customShortUrl = request.getCustomShortUrl();
            ShortUrlEntity newEntity = ShortUrlEntity.builder()
                    .shortUrl(customShortUrl).fullUrl(fullUrl)
                    .build();
            repository.save(newEntity);
            return ShortUrlResponse.builder().key(customShortUrl).build();
        }
    }

    public RedirectView getFullUrl(String shortUrl) {
        ShortUrlEntity entityInDb = repository.findByShortUrl(shortUrl);

        repository.save(entityInDb);
        return new RedirectView(entityInDb.getFullUrl());
    }
}