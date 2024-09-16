package com.losbraulios.hotelmate.DTO;

import com.losbraulios.hotelmate.models.Hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomsResponseDTO {
    private Long id;
    private Long roomNumber;
    private Double nightPrice;
    private Double dayPrice;
    private String roomType;
    private String roomCapacity;
    private Hotel hotel;
}