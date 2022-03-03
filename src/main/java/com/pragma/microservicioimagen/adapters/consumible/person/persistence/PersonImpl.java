package com.pragma.microservicioimagen.adapters.consumible.person.persistence;

import com.pragma.microservicioimagen.domain.exceptions.NotFountException;
import com.pragma.microservicioimagen.domain.models.client.Person;
import com.pragma.microservicioimagen.domain.persistence_ports.client.PersonPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository("personPersistence")
public class PersonImpl implements PersonPersistence {

    private final WebClient.Builder client;
    private final CircuitBreakerFactory factory;

    @Autowired
    public PersonImpl(WebClient.Builder client, CircuitBreakerFactory factory) {
        this.client = client;
        this.factory = factory;
    }

    @Override
    public Mono<Person> read(Long id) {
        CircuitBreaker circuit = factory.create("circuit1");
        return circuit.run( () -> this.client.build().get().uri("/{id}", id)
                .retrieve().bodyToMono(Person.class)
                .onErrorResume(e -> Mono.error(new NotFountException("Not Found Person "))),

                t -> Mono.error(new NotFountException("Not Available Resource"))
        );
    }
}
