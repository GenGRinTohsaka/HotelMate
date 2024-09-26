package com.losbraulios.hotelmate.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.losbraulios.hotelmate.DTO.response.ReservationsResponseDTO;
import com.losbraulios.hotelmate.DTO.save.ReservationsSaveDTO;
import com.losbraulios.hotelmate.models.Clients;
import com.losbraulios.hotelmate.models.Reservations;
import com.losbraulios.hotelmate.models.Rooms;
import com.losbraulios.hotelmate.service.ClientService;
import com.losbraulios.hotelmate.service.ReservationsService;
import com.losbraulios.hotelmate.service.RoomService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("hotelMate/v1/reservations")
public class ReservationController {
    @Autowired
    ReservationsService reservationsService;
    @Autowired
    ClientService clientService;
    @Autowired
    RoomService roomService;

    /*
     * Esta funcion se encarga de devolver las reservaciones realizadas por un cliente
     * El link de esta función es: http://localhost:8081/hotelMate/v1/reservations/{idClient}
     */
    @GetMapping("/{idClient}")
    public ResponseEntity<?> getResevationByClient(@PathVariable Long idClient){
        Map<String, Object> res = new HashMap<>();
        try {
            List<ReservationsResponseDTO> reservations = reservationsService.myReservations(idClient);
            if(reservations == null || reservations.isEmpty()){
                res.put("message", "Aún no tienes reservaciones creadas");
                return ResponseEntity.status(404).body(res);
            }else{
                return ResponseEntity.ok(reservations);
            }
        } catch (CannotCreateTransactionException e) {
            res.put("message", "Error al conectar a la base de datos");
            res.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException e) {
            res.put("message", "Error al consultar con la base de datos");
            res.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception e) {
            res.put("message", "Error general al obtener los datos");
            res.put("Error", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    /*
     * Esta funcion se encarga de registrar las reservaciones 
     * El link de esta función es: http://localhost:8081/hotelMate/v1/reservations/save
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveReservation(
        @Valid @ModelAttribute ReservationsSaveDTO reservationDTO,
        BindingResult result
    ){
        Map<String, Object> res = new HashMap<>();
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
                res.put("Errors", errors);
                return ResponseEntity.badRequest().body(res);
        }   
        try {
            Reservations reservations = reservationsService.save(reservationDTO);
            res.put("message", "Reservación realizada con exito");
            res.put("Reservations", reservations);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("message", "Error al registrar la reservacion, intente de nuevo más tarde");
            res.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    /*
     * Esta funcion se encarga de devolver una Reservacion por medio de su ID
     * El link de esta función es: http://localhost:8081/hotelMate/v1/reservations/search/{idReservation}
     */
    @GetMapping("/search/{idReservation}")
    public ResponseEntity<?> myReservationById(@PathVariable Long idReservation){
        Map<String, Object> res = new HashMap<>();
        try {
            Reservations reservations = reservationsService.findByIdReservations(idReservation);
            if (reservations != null) {
                return ResponseEntity.ok().body(reservations);
            } else {
                res.put("message", "Reservacion no encontrada");
                return ResponseEntity.status(404).body(res);
            }
        } catch (CannotCreateTransactionException e) {
            res.put("message", "Error al conectar con la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException e) {
            res.put("message", "Error al consultar la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception e) {
            res.put("message", "Error general al obtener la reservacion");
            res.put("error", e);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    /*
     * Esta funcion se encarga de eliminar una Reservacion por medio de su ID
     * El link de esta función es: http://localhost:8081/hotelMate/v1/reservations/delete/{idReservation}
     */
    @DeleteMapping("/delete/{idReservation}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long idReservation){
        Map<String, Object> res = new HashMap<>();
        try {
            Reservations reservations = reservationsService.findByIdReservations(idReservation);
            if (reservations != null) {
                reservationsService.eliminate(reservations);
                res.put("message", "Reservación eliminado con exito");
                return ResponseEntity.ok().body(res);
            }else{
                res.put("message", "No se enconto una reservacion con el ID"+ idReservation);
                return ResponseEntity.status(404).body(res);
            }
        } catch (CannotCreateTransactionException e) {
            res.put("message", "Error al conectar con la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException e) {
            res.put("message", "Error al eliminar la reservacion de la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception e) {
            res.put("message", "Error general al cancelar la reservacion");
            res.put("error", e);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    /*
     * Esta funcion se encarga de actualizar una Reservacion el cual se busca por medio de su ID
     * El link de esta función es: http://localhost:8081/hotelMate/v1/reservations/update/{idReservation}
     */
    @PutMapping("/update/{idReservation}")
    public ResponseEntity<?> updateMyReservation(@PathVariable Long idReservation, @RequestBody ReservationsSaveDTO newReservation){
        Map<String, Object> res = new HashMap<>();
        try {
            Reservations oldReservation = reservationsService.findByIdReservations(idReservation);
            Clients client = clientService.findByIdClient(oldReservation.getClients().getIdClient());
            Rooms room = roomService.findByIdRoom(oldReservation.getRoom().getRoomId());
            if (oldReservation == null) {
                res.put("message","No se encontro una Reservacion con el ID " + idReservation);
                return ResponseEntity.status(404).body(res);
            }if (newReservation.getDescriptionReservation() == null) {
                newReservation.setDescriptionReservation(oldReservation.getDescriptionReservation());
            }if (newReservation.getStarDate() == null) {
                newReservation.setStarDate(oldReservation.getStarDate().toLocalDateTime());
            }if (newReservation.getEndDate() == null) {
                newReservation.setEndDate(oldReservation.getEndDate().toLocalDateTime());
            }if (newReservation.getRoomId() == null) {
                newReservation.setRoomId(room.getRoomId());
            } if(newReservation.getClientsId() == null){
                newReservation.setClientsId(client.getIdClient());
            } if(newReservation.getIdReservation() == null){
                newReservation.setIdReservation(idReservation);
            }
            reservationsService.save(newReservation);
            return ResponseEntity.ok().body(newReservation);
        } catch (CannotCreateTransactionException e) {
            res.put("message", "Error al conectar con la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException e) {
            res.put("message", "Error al actualizar la reservacion en la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception e) {
            res.put("message", "Error general al actualizar la reservacion");
            res.put("error", e);
            return ResponseEntity.internalServerError().body(res);
        }

    }
}
