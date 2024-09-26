package com.losbraulios.hotelmate.service.IService;

import java.util.List;

import com.losbraulios.hotelmate.DTO.response.ReservationsResponseDTO;
import com.losbraulios.hotelmate.DTO.save.ReservationsSaveDTO;
import com.losbraulios.hotelmate.models.Reservations;

public interface IReservationsService {
    List<ReservationsResponseDTO> myReservations(Long idClient);
    
    Reservations findByIdReservations(Long idReservation);

    Reservations save(ReservationsSaveDTO reservationDTO);

    void eliminate(Reservations reservations);
}
