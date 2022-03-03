package com.pragma.microservicioimagen.domain.models.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private Long id;
    private String data;
    private Person person;
    private Integer hasCode;

}
