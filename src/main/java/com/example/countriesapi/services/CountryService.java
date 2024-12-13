package com.example.countriesapi.services;


import com.example.countriesapi.dto.CountryDTO;
import com.example.countriesapi.models.Country;
import com.example.countriesapi.repositories.CountryRepository;
import org.springframework.stereotype.Service;


@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public void saveCountry(CountryDTO countryDTO) {
        Country country = new Country(countryDTO);

        countryRepository.save(country);
    }

    public boolean isDataLoaded() {
        return countryRepository.count() > 0;
    }
}
