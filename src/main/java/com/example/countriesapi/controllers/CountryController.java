package com.example.countriesapi.controllers;

import com.example.countriesapi.dto.CountryDTO;
import com.example.countriesapi.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/{isoCode}")
    public ResponseEntity<CountryDTO> getCountry(@PathVariable String isoCode) {
        CountryDTO countryDTO = countryService.getCountryByIsoCode(isoCode);

        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic())
                .eTag(String.valueOf(countryDTO.hashCode()))
                .body(countryDTO);
    }

    @GetMapping
    public ResponseEntity<List<CountryDTO>> getAllCountries() {
        List<CountryDTO> countryDTOs = countryService.getAllCountries();

        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic())
                .eTag(String.valueOf(countryDTOs.hashCode()))
                .body(countryDTOs);
    }
}
