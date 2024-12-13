package com.example.countriesapi.exceptions;

public class CountryNotFound extends RuntimeException {
    public CountryNotFound(String isoCode) {
        super(String.format("Country with ISO code '%s' could not be found.", isoCode));
    }

    public CountryNotFound(String message, String isoCode) {
        super(message + isoCode);
    }
}
