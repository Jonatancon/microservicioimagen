package com.pragma.microservicioimagen.domain.services.client;

import com.pragma.microservicioimagen.domain.exceptions.ConflictException;
import com.pragma.microservicioimagen.domain.exceptions.NotFountException;
import com.pragma.microservicioimagen.domain.input_ports.client.ImagesPort;
import com.pragma.microservicioimagen.domain.models.client.Image;
import com.pragma.microservicioimagen.domain.persistence_ports.client.ImagesPersistence;
import com.pragma.microservicioimagen.domain.persistence_ports.client.PersonPersistence;
import com.pragma.microservicioimagen.domain.util.ValidatorsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.UUID;

@Service
public class ImagesService implements ImagesPort {

    private final ImagesPersistence imagesPersistence;
    private final PersonPersistence personPersistence;

    private static final String URL_PATCH = "C:\\Users\\jonatan.restrepo\\Documents\\work-space\\microservicioimagen\\data\\";

    @Autowired
    public ImagesService(ImagesPersistence imagesPersistence, PersonPersistence personPersistence) {
        this.imagesPersistence = imagesPersistence;
        this.personPersistence = personPersistence;
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Image> readAll() {
        return this.imagesPersistence.readAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Image> read(long id) {
        return this.imagesPersistence.read(id)
                .switchIfEmpty(Mono.error(new NotFountException("Not Found Image In DataBase")));
    }

    @Override
    @Transactional
    public Mono<Image> create(FilePart file, Long id) {
        this.verifyImage(file);

        String url = UUID.randomUUID() + "-" + file.filename()
                .replace(" ", "-")
                .replace(":", "");

        return file.transferTo(new File(URL_PATCH + url)).then( personPersistence.read(id)
                .switchIfEmpty(Mono.error(new NotFountException("Not Found Person In Micro services Person")))
                .flatMap(person -> imagesPersistence.create(url, person))
        );
    }

    @Override
    @Transactional
    public Mono<Image> update(Long id, FilePart file) {
        this.verifyImage(file);

        String url = UUID.randomUUID() + "-" + file.filename()
                .replace(" ", "-")
                .replace(":", "")
                .replace("\\", "");

        return file.transferTo(new File(URL_PATCH + url)).then(this.imagesPersistence.read(id)
                .switchIfEmpty(Mono.error(new NotFountException("Not Found Image")))
                .then(this.imagesPersistence.update(id, url))
        );
    }

    @Override
    @Transactional
    public Mono<String> delete(Long id) {
        return this.imagesPersistence.read(id)
                .switchIfEmpty(Mono.error(new NotFountException("Not Found Image For Delete")))
                .then(this.imagesPersistence.delete(id));
    }

    @Override
    @Transactional
    public Mono<String> deleteAllImagesFromPerson(Long id) {
        return this.imagesPersistence.deleteAll(id);
    }

    public void verifyImage(FilePart file){
        if (ValidatorsUtil.isEmptyFile(file)) {
            throw new ConflictException("No Image upload, please select a image");
        }
    }
}
