package com.pragma.microservicioimagen.adapters.mongodb.entity.client;

import com.pragma.microservicioimagen.domain.models.client.Image;
import com.pragma.microservicioimagen.domain.models.client.Person;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;

@Getter
@Setter
@Document
public class ImageEntity {

    @Id
    private Long id;
    private String data;
    private PersonEntity personEntity;

    public ImageEntity() {
        this.id = UUID.randomUUID().getLeastSignificantBits();
    }

    public ImageEntity(String imageData, PersonEntity personEntity) {
        this.id = UUID.randomUUID().getLeastSignificantBits();
        this.data = imageData;
        this.personEntity = personEntity;
    }

    public static Image toImage(ImageEntity imageEntity) {
        Image image = new Image();
        Person person = imageEntity.getPersonEntity().toPerson();
        BeanUtils.copyProperties(imageEntity, image);
        image.setPerson(person);
        image.setHasCode(imageEntity.getData().hashCode());
        return image;
    }
}
