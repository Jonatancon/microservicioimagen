package com.pragma.microservicioimagen.adapters.mongodb.persistence.client;

import com.pragma.microservicioimagen.adapters.mongodb.daos.client.ImageMongoRepository;
import com.pragma.microservicioimagen.adapters.mongodb.entity.client.ImageEntity;
import com.pragma.microservicioimagen.adapters.mongodb.entity.client.PersonEntity;
import com.pragma.microservicioimagen.domain.models.client.Image;
import com.pragma.microservicioimagen.domain.models.client.Person;
import com.pragma.microservicioimagen.domain.persistence_ports.client.ImagesPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository("imagesPersistence")
public class ImagePersistenceMongo implements ImagesPersistence {
    private final ImageMongoRepository imageRepository;

    @Autowired
    public ImagePersistenceMongo(ImageMongoRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Flux<Image> readAll() {
        return this.imageRepository.findAll().map(ImageEntity::toImage);
    }

    @Override
    public Mono<Image> read(long id) {
        return this.imageRepository.findById(id).map(ImageEntity::toImage);
    }

    @Override
    public Mono<Image> create(String file, Person person) {
        PersonEntity personEntity = new PersonEntity(person);
        ImageEntity image = new ImageEntity(file, personEntity);
        return this.imageRepository.save(image).map(ImageEntity::toImage);
    }

    @Override
    public Mono<Image> update(Long id, String file) {
        Mono<ImageEntity> image = this.imageRepository.findById(id);

        return image.flatMap(imagen -> {
            imagen.setData(file);
            return imageRepository.save(imagen);
        }).map(ImageEntity::toImage);
    }

    @Override
    public Mono<String> delete(Long id) {
        return this.imageRepository.deleteById(id).then(Mono.just("Image Delete"));
    }

    @Override
    public Mono<String> deleteAll(Long id) {
        return this.imageRepository.deleteAllByPersonEntity_Id(id).then(Mono.just("All Image Delete for the ID: " + id));
    }
}
