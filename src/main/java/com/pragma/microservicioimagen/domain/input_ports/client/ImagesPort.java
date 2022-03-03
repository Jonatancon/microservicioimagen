package com.pragma.microservicioimagen.domain.input_ports.client;

import com.pragma.microservicioimagen.domain.models.client.Image;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImagesPort {

    Flux<Image> readAll();

    Mono<Image> read(long id);

    Mono<Image> create(FilePart file, Long id);

    Mono<Image> update(Long id, FilePart file);

    Mono<String> delete(Long id);

    Mono<String> deleteAllImagesFromPerson(Long id);
}
