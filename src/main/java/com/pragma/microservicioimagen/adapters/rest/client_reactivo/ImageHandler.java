package com.pragma.microservicioimagen.adapters.rest.client_reactivo;

import com.pragma.microservicioimagen.domain.input_ports.client.ImagesPort;
import com.pragma.microservicioimagen.domain.models.client.Image;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import static org.springframework.web.reactive.function.BodyInserters.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public record ImageHandler(ImagesPort imagesPort) {


    public Mono<ServerResponse> readAll(ServerRequest ignoredRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(imagesPort.readAll(), Image.class);
    }

    public Mono<ServerResponse> read(ServerRequest request) {
        long id = Long.parseLong(request.pathVariable("id"));

        return imagesPort.read(id).flatMap(photo -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(photo))
        );
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        long id = Long.parseLong(request.pathVariable("id"));

        return request.multipartData().map(file -> file.toSingleValueMap().get("file"))
                .cast(FilePart.class)
                .flatMap(file -> imagesPort.create(file, id))
                .flatMap(response -> ServerResponse
                        .created(URI.create("/api/v2/image"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(response))
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        long id = Long.parseLong(request.pathVariable("id"));

        return request.multipartData().map(file -> file.toSingleValueMap().get("file"))
                .cast(FilePart.class)
                .flatMap(file -> imagesPort.update(id, file))
                .flatMap(response -> ServerResponse
                        .created(URI.create("/api/v2/image"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(response))
                );
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        long id = Long.parseLong(request.pathVariable("id"));

        return imagesPort.deleteAllImagesFromPerson(id).flatMap(response -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(response))
        );
    }


}
