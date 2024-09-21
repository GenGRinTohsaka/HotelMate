package com.losbraulios.hotelmate.service.IService;

import java.util.List;
import com.losbraulios.hotelmate.DTO.ReservationsResponseDTO;
import com.losbraulios.hotelmate.DTO.ReservationsSaveDTO;
import com.losbraulios.hotelmate.models.Reservations;

public interface IReservationsService {
    List<ReservationsResponseDTO> myReservations();//Long roomId ,Long idClient);

    Reservations findByIdReservations(Long idReservation);

    Reservations save(ReservationsSaveDTO reservationDTO);

    void eliminate(Reservations reservations);
}
