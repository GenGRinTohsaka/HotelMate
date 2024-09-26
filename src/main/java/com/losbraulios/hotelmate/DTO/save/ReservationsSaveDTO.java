package com.losbraulios.hotelmate.DTO.save;

import java.time.LocalDateTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationsSaveDTO {
    private Long idReservation;
    @NotBlank(message = "La descripción no puede ir vacía")
    private String descriptionReservation;
    @NotNull(message = "La fecha de inicio no puede ir vacía")
    @FutureOrPresent
    private LocalDateTime starDate;
    @NotNull(message = "La fecha de finalización no puede ir vacío")
    @FutureOrPresent
    private LocalDateTime endDate;
    @NotNull(message = "No se ha asignado una habitación")
    private Long roomId;
    @NotNull(message = "No se ha asignao un Cliente")
    private Long clientsId;
}
