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
import com.losbraulios.hotelmate.models.Rooms;
import com.losbraulios.hotelmate.service.RoomService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("hotelMate/v1/rooms")
public class RoomController {

    @Autowired
    RoomService roomService;

    /*
     * Metodo para listar las habitaciones
     * La linea de coneccion para el metodo get es:
     * http://localhost:8081/hotelMate/v1/rooms
     */
    @GetMapping()
    public ResponseEntity<?> getRooms() {
        Map<String, Object> res = new HashMap<>();
        try {
            return ResponseEntity.ok().body(roomService.listHabitaciones());
        } catch (CannotCreateTransactionException err) {
            res.put("message", "Error al conectar a la Base de Datos");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException err) {
            res.put("message", "Error al consultar con la base de datos");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception err) {
            res.put("message", "Error general al obtener los datos");
            res.put("Error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

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
           
            Rooms room = roomService.register(rooms);
            res.put("message", "Habitacion agregada correctamente");
            return ResponseEntity.ok(res);
        } catch (Exception err) {
            res.put("message", "Error al guardar la habitacion, intente de nuevo");
            res.put("error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    /*
     * Esta función nos devuelve una habitacion especifico en base al id
     * El link de la función es: http://localhost:8081/hotelMate/v1/rooms/search/{roomId}
     */
    @GetMapping("/search/{roomId}")
    public ResponseEntity<?> searchRoom(@PathVariable Long roomId) {
        Map<String, Object> res = new HashMap<>();
        try {
            Rooms rooms = roomService.gRooms(roomId);
            if (rooms != null) {
                return ResponseEntity.ok().body(rooms);
            } else {
                res.put("message", "Habitacion no encontrada");
                return ResponseEntity.status(404).body(res);
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
            res.put("message", "Error general al obtener la habitacion");
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
            Rooms rooms = roomService.gRooms(roomId);
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
    public ResponseEntity<?> updateRoom(@PathVariable Long roomId, @RequestBody Rooms newRoom) {
        Map<String, Object> res = new HashMap<>();
        try {
            Rooms oldRoom = roomService.gRooms(roomId);
            if (oldRoom == null) {
                res.put("message", "No se encontró habitacion con el id:" + roomId);
                return ResponseEntity.status(404).body(res);
            }

            //Todas estas validaciones son para que los datos anteriores tomen lugar de los valores null en el RequestBody
            if (newRoom.getDayPrice() != null) {
                oldRoom.setDayPrice(newRoom.getDayPrice());
            }
            if (newRoom.getNightPrice() != null) {
                oldRoom.setNightPrice(newRoom.getNightPrice());
            }
            if (newRoom.getRoomCapacity() != null) {
                oldRoom.setRoomCapacity(newRoom.getRoomCapacity());
            }
            if (newRoom.getRoomNumber() != null) {
                oldRoom.setRoomNumber(newRoom.getRoomNumber());
            }
            if (newRoom.getRoomType() != null) {
                oldRoom.setRoomType(newRoom.getRoomType());
            }
            roomService.register(oldRoom);
            return ResponseEntity.ok().body(oldRoom);
            

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
