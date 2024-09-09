package com.losbraulios.hotelmate.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HotelRegisterDTO {
    @NotBlank
    private String direccion;
    @NotBlank
    private String nombreHotel;
    @NotBlank
    private String telefono;
    @NotBlank
    private String categoria;
}
