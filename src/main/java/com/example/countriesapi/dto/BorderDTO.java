package com.example.countriesapi.dto;

import com.example.countriesapi.models.Border;

public record BorderDTO(
        String countryCode,
        String neighbourCode
) {
    public BorderDTO(String countryCode, String neighbourCode) {
        this.countryCode = countryCode;
        this.neighbourCode = neighbourCode;
    }

    public BorderDTO() {
        this("", "");
    }

    public BorderDTO(Border border) {
        this(border.getCountry().getIsoCode(), border.getNeighbour().getIsoCode());
    }
}
