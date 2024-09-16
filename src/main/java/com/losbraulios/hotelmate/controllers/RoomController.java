package com.losbraulios.hotelmate.controllers;

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

import com.losbraulios.hotelmate.DTO.RoomsAssignmentDTO;
import com.losbraulios.hotelmate.DTO.RoomsResponseDTO;
import com.losbraulios.hotelmate.models.Hotel;
import com.losbraulios.hotelmate.models.Rooms;
import com.losbraulios.hotelmate.service.HotelService;
import com.losbraulios.hotelmate.service.RoomService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("hotelMate/v1/rooms")
public class RoomController {

    @Autowired
    RoomService roomService;

    @Autowired
    HotelService hotelService;
    
    /*
     * Metodo para crear habitaciones
     * La linea de coneccion para crear habitaciones es:
     * http://localhost:8081/hotelMate/v1/rooms/assignment
     */
    @PostMapping("/assignment")
    public ResponseEntity<?> register(
            @Valid @ModelAttribute RoomsAssignmentDTO rooms,
            BindingResult result) {
        Map<String, Object> res = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            res.put("message", "Error con las validaciones favor ingresar todos los campos");
            res.put("Errores", errors);
            return ResponseEntity.badRequest().body(res);
        }
        try {
           if(rooms.getRoomId() == null){
            rooms.setRoomId(null);
           }
            Rooms room = roomService.save(rooms);
            res.put("message", "Habitacion agregada correctamente");
            res.put("habitacion", room);
            return ResponseEntity.ok(res);
        } catch (Exception err) {
            res.put("message", "Error al guardar la habitacion, intente de nuevo");
            res.put("error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    /*
     * Esta función nos devuelve una habitacion especifico en base al id
     * El link de la función es: http://localhost:8081/hotelMate/v1/rooms/search/{hotelId}
     */
    @GetMapping("/search/{hotelId}")
    public ResponseEntity<?> searchRoom(@PathVariable Long hotelId) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<RoomsResponseDTO> rooms = roomService.myRooms(hotelId);
            if(rooms == null){
                res.put("message", "No existen habitaciones asignadas al hotel");
                return ResponseEntity.status(404).body(res);
            }else{
                return ResponseEntity.ok(rooms);
            }

        } catch (CannotCreateTransactionException err) {
            res.put("message", "Error al conectar con la base de datos");
            res.put("error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException err) {
            res.put("message", "Error al consultar la base de datos");
            res.put("error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception err) {
            res.put("message", "Error general al obtener las habitaciones");
            res.put("error", err);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    /*
     * Metodo para eliminar habitaciones
     * el link del metodo es:
     * http://localhost:8081/hotelMate/v1/rooms/delete/{roomId}
     */
    @DeleteMapping("/delete/{roomId}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long roomId) {
        Map<String, Object> res = new HashMap<>();
        try {
            Rooms rooms = roomService.findByIdRoom(roomId);
            if (rooms != null) {
                roomService.eliminate(rooms);
                res.put("message", "Habitacion eliminada con exito");
                return ResponseEntity.ok().body(res);
            } else {
                res.put("message", "No se encontró la habitación con el ID:" + roomId);
                return ResponseEntity.status(404).body(res);
            }
        } catch (CannotCreateTransactionException err) {
            res.put("message", "Error al conectar con la base de datos");
            res.put("error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException err) {
            res.put("message", "Error al eliminar la habitacion de la base de datos");
            res.put("error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception err) {
            res.put("message", "Error general al eliminar la habitacion");
            res.put("error", err);
            return ResponseEntity.internalServerError().body(res);

        }
    }

    /*
     * Esta función nos actualiza los datos
     * El link de la función es: http://localhost:8081/hotelMate/v1/rooms/update/{roomId}
     */
    @PutMapping("/update/{roomId}")
    public ResponseEntity<?> updateRoom(@PathVariable Long roomId, @RequestBody RoomsAssignmentDTO newRoom) {
        Map<String, Object> res = new HashMap<>();
        try {
            Rooms oldRoom = roomService.findByIdRoom(roomId);
            Hotel hotel = hotelService.getHotel(newRoom.getIdHotel());
            if (oldRoom == null) {
                res.put("message", "No se encontró habitacion con el id:" + roomId);
                return ResponseEntity.status(404).body(res);
            }

            //Todas estas validaciones son para que los datos anteriores tomen lugar de los valores null en el RequestBody
            if (newRoom.getDayPrice() == null) {
                newRoom.setDayPrice(oldRoom.getDayPrice());
            }
            if (newRoom.getNightPrice() == null) {
                newRoom.setNightPrice(oldRoom.getNightPrice());
            }
            if (newRoom.getRoomCapacity() == null) {
                newRoom.setRoomCapacity(oldRoom.getRoomCapacity());
            }
            if (newRoom.getRoomNumber() == null) {
                newRoom.setRoomNumber(oldRoom.getRoomNumber());
            }
            if (newRoom.getRoomType() == null) {
                newRoom.setRoomType(oldRoom.getRoomType());
            }if(newRoom.getIdHotel() == null){
                newRoom.setIdHotel(hotel.getIdHotel());
            }if(newRoom.getRoomId() == null){
                newRoom.setRoomId(oldRoom.getRoomId());
            }
            
            roomService.save(newRoom);
            return ResponseEntity.ok().body(newRoom);
            

        } catch (CannotCreateTransactionException err) {
            res.put("message", "Error al conectar con la base de datos");
            res.put("error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException err) {
            res.put("message", "Error al actualizar la habitacion en la base de datos");
            res.put("error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception err) {
            res.put("message", "Error general al actualizar la habitacion");
            res.put("error", err);
            return ResponseEntity.internalServerError().body(res);
        }
    }
}
