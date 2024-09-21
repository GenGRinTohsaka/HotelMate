package com.losbraulios.hotelmate.DTO;

import java.sql.Timestamp;
import com.losbraulios.hotelmate.models.Clients;
import com.losbraulios.hotelmate.models.Rooms;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationsResponseDTO {
    private Long idReservation;
    private String descriptionReservation;
    private Timestamp starDate;
    private Timestamp endDate;
    private Rooms room;
    private Clients clients;
}