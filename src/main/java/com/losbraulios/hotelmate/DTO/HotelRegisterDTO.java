package com.losbraulios.hotelmate.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HotelRegisterDTO {
    @NotBlank
    private String direction;
    @NotBlank
    private String nameHotel;
    @NotBlank
    private String phone;
    @NotBlank
    private String category;
}
