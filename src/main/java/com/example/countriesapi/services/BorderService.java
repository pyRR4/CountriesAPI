package com.example.countriesapi.services;


import com.example.countriesapi.dto.BorderDTO;
import com.example.countriesapi.models.Border;
import com.example.countriesapi.repositories.BorderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BorderService {

    private final BorderRepository borderRepository;

    @Autowired
    public BorderService(BorderRepository borderRepository) {
        this.borderRepository = borderRepository;
    }

    public List<Border> getBordersByIsoCode(String isoCode) {
        List<Border> borders = borderRepository.findByCountryIsoCode(isoCode);
        List<Border> bordersAsNeighbour = borderRepository.findByNeighbourIsoCode(isoCode);

        List<Border> allBorders = new ArrayList<>();
        allBorders.addAll(bordersAsNeighbour);
        allBorders.addAll(borders);

        return allBorders
                .stream()
                .toList();
    }

    public Border saveBorder(BorderDTO borderDTO) {
        Border border = new Border(borderDTO);

        return borderRepository.save(border);
    }
}
