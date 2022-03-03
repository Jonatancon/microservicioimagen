package com.pragma.microservicioimagen.adapters.rest.client;

import com.pragma.microservicioimagen.domain.input_ports.client.ImagesPort;
import com.pragma.microservicioimagen.domain.models.client.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ImagesResources.IMAGES)
@Slf4j
public class ImagesResources {
    static final String IMAGES = "/api/v1/image";

    private final ImagesPort imagesPort;

    @Autowired
    public ImagesResources(ImagesPort imagesPort) {
        this.imagesPort = imagesPort;
    }

    @GetMapping
    public Flux<Image> readAll() {
        return this.imagesPort.readAll();
    }

    @GetMapping("/{id}")
    public Mono<Image> read(@PathVariable long id) {
        return this.imagesPort.read(id);
    }

    @PostMapping("/{id}")
    public Mono<Image> create(@PathVariable Long id, @RequestPart FilePart file) {
        return this.imagesPort.create(file, id);
    }

    @PutMapping("/{id}")
    public Mono<Image> update(@PathVariable Long id, @RequestPart FilePart file) {
        return this.imagesPort.update(id, file);
    }

    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable Long id) {
        return this.imagesPort.deleteAllImagesFromPerson(id);
    }
}
