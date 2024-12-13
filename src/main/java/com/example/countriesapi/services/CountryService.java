package com.example.countriesapi.services;


import com.example.countriesapi.dto.BorderDTO;
import com.example.countriesapi.dto.CountryDTO;
import com.example.countriesapi.exceptions.CountryNotFound;
import com.example.countriesapi.models.Border;
import com.example.countriesapi.models.Country;
import com.example.countriesapi.repositories.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;


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

        List<Border> countryBorders = borderService.getBordersByIsoCode(isoCode);
        country.setBorders(countryBorders);

        return new CountryDTO(country);
    }

    public void addCountryBorder(BorderDTO borderDTO) {
        Country country = countryRepository.findByIsoCode(borderDTO.countryCode())
                .orElseThrow(() -> new CountryNotFound(borderDTO.countryCode()));

        Country neighbour = countryRepository.findByIsoCode(borderDTO.neighbourCode())
                .orElseThrow(() -> new CountryNotFound(borderDTO.neighbourCode()));

        Border border = borderService.saveBorder(borderDTO);
        country.getBorders().add(border);
        neighbour.getBorders().add(border);
    }

    public void saveCountry(CountryDTO countryDTO) {
        Country country = new Country(countryDTO);

        countryRepository.save(country);
    }

    public boolean isDataLoaded() {
        return countryRepository.count() > 0;
    }
}
