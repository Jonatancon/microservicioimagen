package com.pragma.microservicioimagen.adapters.mongodb.daos.client;

import com.pragma.microservicioimagen.adapters.mongodb.entity.client.ImageEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ImageMongoRepository extends ReactiveMongoRepository<ImageEntity, Long> {
    Mono<Void> deleteAllByPersonEntity_Id(Long id);
}
