package com.example.countriesapi.models;


import com.example.countriesapi.dto.BorderDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "borders",
        uniqueConstraints = @UniqueConstraint(columnNames = {"country_code", "neighbour_code"})
)
@Getter
@Setter
@NoArgsConstructor
public class Border {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "country_code")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "neighbour_code")
    private Country neighbour;

    public Border(Country country, Country neighbour) {
        if (country.getIsoCode().compareTo(neighbour.getIsoCode()) < 0) {
            this.country = country;
            this.neighbour = neighbour;
        } else {
            this.country = neighbour;
            this.neighbour = country;
        }
    }

    public Border(BorderDTO borderDTO) {
        this(
                new Country(borderDTO.countryCode()),
                new Country(borderDTO.neighbourCode())
        );
    }
}
