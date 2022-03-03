package com.pragma.microservicioimagen.adapters.rest.client_reactivo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterFunctionConfig {

    public static final String IMAGE_URL = "/api/v2/image";
    public static final String ID = "/{id}";

    @Bean
    public RouterFunction<ServerResponse> routes (ImageHandler handler) {
        return route(GET(IMAGE_URL), handler::readAll)
                .andRoute(GET(IMAGE_URL + ID), handler::read)
                .andRoute(POST(IMAGE_URL + ID), handler::create)
                .andRoute(PUT(IMAGE_URL + ID), handler::update)
                .andRoute(DELETE(IMAGE_URL + ID), handler::delete);
    }
}
