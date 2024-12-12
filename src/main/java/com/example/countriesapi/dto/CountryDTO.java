package com.example.countriesapi.dto;

import com.example.countriesapi.models.Country;

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
    Set<String> timeZones
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
            Set<String> timeZones
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
    }

    public CountryDTO() {
        this(
                "",
                "",
                "",
                "",
                "",
                0L,
                Set.of(),
                Set.of(),
                Set.of(),
                Set.of()
        );
    }

    public CountryDTO(Country country) {
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
                country.getTimeZones()
        );
    }
}
