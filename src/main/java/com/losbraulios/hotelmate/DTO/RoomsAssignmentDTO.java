package com.losbraulios.hotelmate.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoomsAssignmentDTO {

    @NotBlank(message = "El numero de habitacion no puede estar vacio")
    private long roomNumber;
    @NotBlank(message = "El precio no puede ir vacio")
    private double nightPrice;
    @NotBlank(message = "El precio no puede ir vacio")
    private double dayPrice;
    @NotBlank(message = "El tipo de la habitacion no puede ir vacia")
    private String roomType;
    @NotBlank(message = "La capacidad no puede ir vacia")
    private String roomCapacity;
}
