package com.example.countriesapi.services;


import com.example.countriesapi.dto.BorderDTO;
import com.example.countriesapi.dto.CountryDTO;
import com.example.countriesapi.exceptions.CountryNotFound;
import com.example.countriesapi.models.Country;
import com.example.countriesapi.repositories.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CountryService {

    private final CountryRepository countryRepository;
    private final BorderService borderService;

    public CountryService(CountryRepository countryRepository, BorderService borderService) {
        this.countryRepository = countryRepository;
        this.borderService = borderService;
    }

    public CountryDTO getCountryByIsoCode(String isoCode) {
        Country country = countryRepository.findByIsoCode(isoCode)
                .orElseThrow(() -> new CountryNotFound(isoCode));

        List<BorderDTO> countryBorders = borderService.getBordersByIsoCode(isoCode);

        return new CountryDTO(country, countryBorders);
    }

    public List<CountryDTO> getAllCountries() {
        List<Country> countries = countryRepository.findAll();

        if(countries.isEmpty()) {
            throw new CountryNotFound("No countries found", "");
        }

        return countries.stream()
                .map(country -> {
                    List<BorderDTO> countryBorders = borderService.getBordersByIsoCode(country.getIsoCode());
                    return new CountryDTO(country, countryBorders);
                })
                .collect(Collectors.toList());
    }

    public void saveCountry(CountryDTO countryDTO) {
        Country country = new Country(countryDTO);

        countryRepository.save(country);
    }

    public boolean isDataLoaded() {
        return countryRepository.count() > 0;
    }
}
