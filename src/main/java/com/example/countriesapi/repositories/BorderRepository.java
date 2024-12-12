package com.example.countriesapi.repositories;

import com.example.countriesapi.models.Border;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorderRepository extends JpaRepository<Border, Long> {
}
