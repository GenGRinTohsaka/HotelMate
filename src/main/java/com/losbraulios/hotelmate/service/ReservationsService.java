package com.losbraulios.hotelmate.service;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losbraulios.hotelmate.DTO.ReservationsResponseDTO;
import com.losbraulios.hotelmate.DTO.ReservationsSaveDTO;
import com.losbraulios.hotelmate.models.Clients;
import com.losbraulios.hotelmate.models.Reservations;
import com.losbraulios.hotelmate.models.Rooms;
import com.losbraulios.hotelmate.models.Clients;
import com.losbraulios.hotelmate.repository.ReservationsRepository;
import com.losbraulios.hotelmate.service.IService.IReservationsService;


@Service
public class ReservationsService implements IReservationsService{
    @Autowired
    ReservationsRepository reservationRepository;

    @Autowired
    RoomService roomService;

    @Autowired
    ClientsService clientsService;

    @Override
    public List<ReservationsResponseDTO> myReservations(){
       List<Reservations> reservations = reservationRepository.findAll();
       return reservations.stream().map(reservation -> new ReservationsResponseDTO(
       reservation.getIdReservation(),
       reservation.getDescriptionReservation(),
       reservation.getStarDate(),
       reservation.getEndDate(),
       reservation.getRoom(),
       reservation.getClients())).collect(Collectors.toList());
    }

    @Override
    public Reservations findByIdReservations(Long idReservation) {
        return reservationRepository.findById(idReservation).orElse(null);
    }

    @Override
    public Reservations save(ReservationsSaveDTO reservationDTO) {
        try{
            Timestamp starDate = Timestamp.valueOf(reservationDTO.getStarDate());
            Timestamp endDate = Timestamp.valueOf(reservationDTO.getEndDate());
            Clients clients = clientsService.findFieldById(reservationDTO.getClientsId());
            Rooms rooms = roomService.findFieldById(reservationDTO.getRoomId());
            Reservations reservation = new Reservations(
                reservationDTO.getIdReservation(),
                reservationDTO.getDescriptionReservation(),
                starDate,
                endDate,
                rooms,
                clients
            );
            return reservationRepository.save(reservation);
        }catch(Exception e){
            throw new IllegalArgumentException("Error al parsear las fechas", e);
        }
    }

    @Override
    public void eliminate(Reservations reservations) {
        reservationRepository.delete(reservations);
    }

    private ReservationsResponseDTO reservationsResponseDTO(Reservations reservations){
        ReservationsResponseDTO dto = new ReservationsResponseDTO(
            reservations.getIdReservation(),
            reservations.getDescriptionReservation(),
            reservations.getStarDate(),
            reservations.getEndDate(),
            reservations.getRoom(),
            reservations.getClients()
        );
        return dto;
       
    }
}