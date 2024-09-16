package com.losbraulios.hotelmate.DTO;


import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventsSaveDTO {
    @NotBlank(message =  "El nombre del evento no puede ir vacío")
    private String eventName;
    @NotBlank(message = "La descripción del evento no puede ir vacío")
    private String eventDescription;
    @NotNull(message = "El día de inicio no pude ir vacio")
    @FutureOrPresent
    private LocalDateTime startDate;
    @NotNull(message = "El día final no puede ir vacío")
    @FutureOrPresent
    private LocalDateTime endDate;
    @NotNull(message = "No selecciono un servicio")
    private Long serviceId;
}
