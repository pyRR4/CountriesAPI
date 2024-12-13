package com.example.countriesapi.services;


import com.example.countriesapi.dto.BorderDTO;
import com.example.countriesapi.models.Border;
import com.example.countriesapi.repositories.BorderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorderService {

    private final BorderRepository borderRepository;

    @Autowired
    public BorderService(BorderRepository borderRepository) {
        this.borderRepository = borderRepository;
    }

    public void saveBorders(List<BorderDTO> borderDTOs) {
        List<Border> borders = borderDTOs
                .stream()
                .map(Border::new).toList();

        borderRepository.saveAll(borders);
    }
}
