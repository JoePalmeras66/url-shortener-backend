package org.joepalmeras.urlshortener.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomShortUrlRequest {
    private String customShortUrl;
    private String url;
}
