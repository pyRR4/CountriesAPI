package com.example.countriesapi.dto;

import com.example.countriesapi.models.Country;

import java.util.List;
import java.util.Set;

public record CountryDTO(
    String isoCode,
    String commonName,
    String officialName,
    String region,
    String subregion,
    Long population,
    Set<String> languages,
    Set<String> currencies,
    Set<String> capital,
    Set<String> timeZones,
    List<String> borders
) {
    public CountryDTO(
            String isoCode,
            String commonName,
            String officialName,
            String region,
            String subregion,
            Long population,
            Set<String> languages,
            Set<String> currencies,
            Set<String> capital,
            Set<String> timeZones,
            List<String> borders
    ) {
        this.isoCode = isoCode;
        this.commonName = commonName;
        this.officialName = officialName;
        this.region = region;
        this.subregion = subregion;
        this.population = population;
        this.languages = languages;
        this.currencies = currencies;
        this.capital = capital;
        this.timeZones = timeZones;
        this.borders = borders;
    }

    public CountryDTO(Country country, List<BorderDTO> borders) {
        this(
                country.getIsoCode(),
                country.getCommonName(),
                country.getOfficialName(),
                country.getRegion(),
                country.getSubregion(),
                country.getPopulation(),
                country.getLanguages(),
                country.getCurrencies(),
                country.getCapital(),
                country.getTimeZones(),
                borders.stream().map(border ->
                        (border.countryCode().equals(country.getIsoCode())) ?
                                border.neighbourCode() : border.countryCode()
                ).toList()
        );
    }
}
