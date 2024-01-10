package org.joepalmeras.urlshortener.repository;

import org.joepalmeras.urlshortener.entity.ShortUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlRepository extends JpaRepository<ShortUrlEntity, Long> {
    ShortUrlEntity findByShortUrl(String key);
    ShortUrlEntity findByFullUrl(String fullUrl);
}
