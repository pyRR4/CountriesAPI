package com.example.countriesapi.services;


import com.example.countriesapi.dto.BorderDTO;
import com.example.countriesapi.exceptions.CountryNotFound;
import com.example.countriesapi.models.Border;
import com.example.countriesapi.models.Country;
import com.example.countriesapi.repositories.BorderRepository;
import com.example.countriesapi.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BorderService {

    private final BorderRepository borderRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public BorderService(BorderRepository borderRepository, CountryRepository countryRepository) {
        this.borderRepository = borderRepository;
        this.countryRepository = countryRepository;
    }

    public List<BorderDTO> getBordersByIsoCode(String isoCode) {
        List<Border> borders = borderRepository.findByCountryIsoCode(isoCode);
        List<Border> bordersAsNeighbour = borderRepository.findByNeighbourIsoCode(isoCode);

        List<Border> allBorders = new ArrayList<>();
        allBorders.addAll(bordersAsNeighbour);
        allBorders.addAll(borders);

        return allBorders
                .stream()
                .map(BorderDTO::new)
                .toList();
    }

    public void saveBorder(BorderDTO borderDTO) {
        Country country = countryRepository.findByIsoCode(borderDTO.countryCode())
                .orElseThrow(() -> new CountryNotFound(borderDTO.countryCode()));
        Country neighbour = countryRepository.findByIsoCode(borderDTO.neighbourCode())
                .orElseThrow(() -> new CountryNotFound(borderDTO.neighbourCode()));

        Border border = new Border(country, neighbour);

        country.getBorders().add(border);
        neighbour.getBorders().add(border);

        borderRepository.save(border);
    }
}
