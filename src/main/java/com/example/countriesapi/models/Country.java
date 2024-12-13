package com.example.countriesapi.models;


import com.example.countriesapi.dto.CountryDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "countries")
public class Country {

    @Id
    @Column(name = "iso_code", nullable = false)
    private String isoCode;

    @Column(name = "common_name")
    private String commonName;

    @Column(name = "official_name", nullable = false)
    private String officialName;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String subregion;

    @Column(nullable = false)
    private Long population;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Border> borders;

    ///Consider expanding those below to separate entity

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> languages;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> currencies;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> capital;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> timeZones;

    public Country(String isoCode, String commonName, String officialName, String region, String subregion, Long population,
                   Set<String> languages, Set<String> currencies, Set<String> capital, Set<String> timeZones) {
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

    public Country(String isoCode) {
        this(
                isoCode,
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

    public Country(CountryDTO countryDTO) {
        this(
                countryDTO.isoCode(),
                countryDTO.commonName(),
                countryDTO.officialName(),
                countryDTO.region(),
                countryDTO.subregion(),
                countryDTO.population(),
                countryDTO.languages(),
                countryDTO.currencies(),
                countryDTO.capital(),
                countryDTO.timeZones()
        );
    }
}
