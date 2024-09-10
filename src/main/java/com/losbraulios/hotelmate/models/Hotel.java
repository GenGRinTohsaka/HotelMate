package com.losbraulios.hotelmate.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHotel;
    @NotBlank
    @Column(unique = true)
    private String direction;
    @NotBlank
    private String nameHotel;
    @NotBlank
    @Column(unique = true)
    private String phone;
    @NotBlank
    private String category;

}
