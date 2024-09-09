package com.losbraulios.hotelmate.models;

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
    private long idHotel;
    @NotBlank
    private String direccion;
    @NotBlank
    private String nombreHotel;
    @NotBlank
    private String telefono;
    @NotBlank
    private String categoria;

}
