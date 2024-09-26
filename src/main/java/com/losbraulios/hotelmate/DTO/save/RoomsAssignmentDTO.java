package com.losbraulios.hotelmate.DTO.save;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoomsAssignmentDTO {

    
    private Long roomId;
    @NotNull(message = "El numero de habitacion no puede estar vacio")
    private Long roomNumber;
    @NotNull(message = "El precio no puede ir vacio")
    private Double nightPrice;
    @NotNull(message = "El precio no puede ir vacio")
    private Double dayPrice;
    @NotBlank(message = "El tipo de la habitacion no puede ir vacia")
    private String roomType;
    @NotBlank(message = "La capacidad no puede ir vacia")
    private String roomCapacity;
    //Ahora con esto podemos trabajar el guardado con relación
    @NotNull(message = "No se selecciono hotel para asignar")
    private Long idHotel;
}
