package com.losbraulios.hotelmate.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
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
    private Long roomId;
    @NotNull
    private Long roomNumber;
    @NotNull
    private Double nightPrice;
    @NotNull
    private Double dailyPrice;
    @NotNull
    private String roomType;
    @NotNull
    private String roomCapacity;

}
