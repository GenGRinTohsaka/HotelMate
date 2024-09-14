package com.losbraulios.hotelmate.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomsAssignmentDTO {

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

     //Ahora con esto podemos trabajar el guardado con relación (dhernandez)
     @NotNull(message = "No se selecciono hotel para asignar")
     private Long roomId;
}
