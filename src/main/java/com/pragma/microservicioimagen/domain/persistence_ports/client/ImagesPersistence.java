package com.pragma.microservicioimagen.domain.persistence_ports.client;

import com.pragma.microservicioimagen.domain.models.client.Image;
import com.pragma.microservicioimagen.domain.models.client.Person;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ImagesPersistence {

    Flux<Image> readAll();

    Mono<Image> read(long id);

    Mono<Image> create(String file, Person person);

    Mono<Image> update(Long id, String file);

    Mono<String> delete(Long id);

    Mono<String> deleteAll(Long id);
}
