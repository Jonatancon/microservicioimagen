package com.pragma.microservicioimagen.domain.persistence_ports.client;

import com.pragma.microservicioimagen.domain.models.client.Person;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonPersistence {
    Mono<Person> read(Long id);
}
