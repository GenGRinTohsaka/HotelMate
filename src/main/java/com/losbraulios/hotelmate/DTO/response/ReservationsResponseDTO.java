package com.losbraulios.hotelmate.DTO.response;



import java.sql.Timestamp;

import com.losbraulios.hotelmate.DTO.clear.ClientsClearDTO;
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
    private ClientsClearDTO clients;
}
