package com.losbraulios.hotelmate.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losbraulios.hotelmate.DTO.clear.ClientsClearDTO;
import com.losbraulios.hotelmate.DTO.response.ReservationsResponseDTO;
import com.losbraulios.hotelmate.DTO.save.ReservationsSaveDTO;
import com.losbraulios.hotelmate.models.Clients;
import com.losbraulios.hotelmate.models.Reservations;
import com.losbraulios.hotelmate.models.Rooms;
import com.losbraulios.hotelmate.repository.ReservationsRepository;
import com.losbraulios.hotelmate.service.IService.IReservationsService;


@Service
public class ReservationsService implements IReservationsService{
    @Autowired
    ReservationsRepository reservationsRepository;
    
    @Autowired
    RoomService roomService;

    @Autowired
    ClientService clientsService;

    @Override
    public List<ReservationsResponseDTO> myReservations(Long idClient){
        Clients clients = clientsService.findByIdClient(idClient);
       List<Reservations> reservations = reservationsRepository.findByClients(clients);
       return reservations.stream().map(reservation -> reservationsResponseDTO(reservation)).collect(Collectors.toList());
    }

    @Override
    public Reservations findByIdReservations(Long idReservation) {
        return reservationsRepository.findById(idReservation).orElse(null);
    }

    @Override
    public Reservations save(ReservationsSaveDTO reservationDTO) {
        try{
            Timestamp startDate = Timestamp.valueOf(reservationDTO.getStarDate());
            Timestamp endDate = Timestamp.valueOf(reservationDTO.getEndDate());  
            Clients clients = clientsService.findByIdClient(reservationDTO.getClientsId());
            Rooms room = roomService.findByIdRoom(reservationDTO.getRoomId());
            Reservations reservation = new Reservations(
            null,
            reservationDTO.getDescriptionReservation(),
            startDate,
            endDate,
            room,
            clients
            );

            return reservationsRepository.save(reservation);
        }catch(Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("Error al parsear las fechas", e);
        }
    }

    @Override
    public void eliminate(Reservations reservations) {
        reservationsRepository.delete(reservations);
    }

    private ReservationsResponseDTO reservationsResponseDTO(Reservations reservations){
        Clients client = reservations.getClients();
        ClientsClearDTO clientDTO = new ClientsClearDTO(
        client.getNameClient(),
        client.getSurnameClient(), 
        client.getEmailClient()
        );

        ReservationsResponseDTO dto = new ReservationsResponseDTO(
            reservations.getIdReservation(),
            reservations.getDescriptionReservation(),
            reservations.getStarDate(),
            reservations.getEndDate(),
            reservations.getRoom(),
            clientDTO
        );
        return dto;
       
    }
}
