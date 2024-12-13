package com.example.countriesapi.utils;


import com.example.countriesapi.dto.BorderDTO;
import com.example.countriesapi.dto.CountryDTO;
import com.example.countriesapi.services.BorderService;
import com.example.countriesapi.services.CountryService;
import com.example.countriesapi.services.CountryTransformer;
import com.example.countriesapi.services.DataFetchingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private final CountryService countryService;
    private final CountryTransformer countryTransformer;
    private final DataFetchingService dataFetchingServiceService;
    private final BorderService borderService;

    @Autowired
    public DataLoader(
            CountryService countryService,
            CountryTransformer countryTransformer,
            DataFetchingService dataFetchingServiceService,
            BorderService borderService
    ) {
        this.countryService = countryService;
        this.countryTransformer = countryTransformer;
        this.dataFetchingServiceService = dataFetchingServiceService;
        this.borderService = borderService;
    }

    @Override
    public void run(String... args) {
        Mono<Boolean> isDataLoadedMono = Mono.just(countryService.isDataLoaded());

        isDataLoadedMono.subscribe(isLoaded -> {
            if (isLoaded) {
                logger.warn("Data is already loaded");
                return;
            }

            dataFetchingServiceService.fetchCountries()
                    .doOnTerminate(() ->
                            logger.info("Fetching countries from DB.")
                    ).flatMap(country -> {
                                CountryDTO countryDTO = countryTransformer.toCountryDTO(country);

                                List<String> borderCodes = (List<String>) country.getOrDefault("borders", List.of());
                                List<BorderDTO> borderDTOs = countryTransformer.toBorderDTO(countryDTO, borderCodes);

                                countryService.saveCountry(countryDTO);

                                logger.info("Saved country: " + countryDTO.commonName());

                                return Mono.just(borderDTOs);
                            })
                    .collectList()
                    .doOnTerminate(() -> logger.info("Fetching borders from DB."))
                    .subscribe(borderDTOList -> {
                        for (List<BorderDTO> borderDTOSubList: borderDTOList)
                            for (BorderDTO borderDTO: borderDTOSubList)
                                try {
                                    borderService.saveBorder(borderDTO);
                                    logger.info("Added border: " + borderDTO);
                                } catch (DataIntegrityViolationException e) {
                                    logger.warn("Border " + borderDTO + " already added.");
                                }
                    });


        }, error -> logger.error("Error while checking if data is loaded: \n" + error.getMessage()));
    }
}
