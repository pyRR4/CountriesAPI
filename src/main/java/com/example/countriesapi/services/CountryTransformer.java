package com.example.countriesapi.services;

import com.example.countriesapi.dto.BorderDTO;
import com.example.countriesapi.dto.CountryDTO;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CountryTransformer {

    public CountryDTO toCountryDTO(Map<String, Object> countryJSON) {
        String isoCode = (String) countryJSON.get("cca3");
        Map<String, Object> names = (Map<String, Object>) countryJSON.get("name");
        String commonName = (String) names.get("common");
        String officialName = (String) names.get("official");
        String region = (String) countryJSON.get("region");
        String subregion = (String) countryJSON.getOrDefault("subregion", "");
        Long population = ((Number) countryJSON.get("population")).longValue();

        Set<String> languages = countryJSON.containsKey("languages") ?
                new HashSet<>(((Map<String, String>) countryJSON.get("languages")).values()) : Set.of();

        Set<String> currencies = countryJSON.containsKey("currencies") ?
                ((Map<String, Map<String, Object>>) countryJSON.get("currencies")).keySet() : Set.of();

        Set<String> capital = countryJSON.containsKey("capital") ?
                new HashSet<>((List<String>) countryJSON.get("capital")) : Set.of();

        Set<String> timeZones = countryJSON.containsKey("timezones") ?
                new HashSet<>((List<String>) countryJSON.get("timezones")) : Set.of();

        return new CountryDTO(
                isoCode,
                commonName,
                officialName,
                region,
                subregion,
                population,
                languages,
                currencies,
                capital,
                timeZones
        );
    }

    public List<BorderDTO> toBorderDTO(CountryDTO countryDTO, List<String> borderCodes) {
        return borderCodes.stream()
                .map(neighbourCode -> new BorderDTO(countryDTO.isoCode(), neighbourCode))
                .collect(Collectors.toList());
    }
}
