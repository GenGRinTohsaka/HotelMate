package com.losbraulios.hotelmate.DTO.response;

import java.security.Timestamp;

import com.losbraulios.hotelmate.models.Clients;
import com.losbraulios.hotelmate.models.Rooms;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationsResponse {
    private Long idReservation;
    private String descriptionReservation;
    private Timestamp starDate;
    private Timestamp endDate;
    private Rooms room;
    private Clients clients;
}
