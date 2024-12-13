package com.example.countriesapi.repositories;

import com.example.countriesapi.models.Border;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorderRepository extends JpaRepository<Border, Long> {
    List<Border> findByCountryIsoCode(String country);
    List<Border> findByNeighbourIsoCode(String neighbour);
}
