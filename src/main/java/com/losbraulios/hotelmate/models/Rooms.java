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
public class Rooms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roomId;
    @NotBlank
    private long roomNumber;
    @NotBlank
    private double nightPrice;
    @NotBlank
    private double dailyPrice;
    @NotBlank
    private String roomType;
    @NotBlank
    private String roomCapacity;

}
