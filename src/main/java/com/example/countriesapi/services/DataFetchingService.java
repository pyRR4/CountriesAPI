package com.example.countriesapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Map;

@Service
public class DataFetchingService {

    private final WebClient webClient; //main entry point for performing request

    @Autowired
    public DataFetchingService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://restcountries.com/v3.1").build();
    }

    public Flux<Map<String, Object>> fetchCountries() {

        return webClient.get()
                .uri("/all")
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<>() {});
    }
}
