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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.losbraulios.hotelmate.DTO.RoomsAssignmentDTO;
import com.losbraulios.hotelmate.models.Rooms;
import com.losbraulios.hotelmate.service.RoomService;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.Valid;

@RestController
@RequestMapping("hotelMate/v1/rooms")
public class RoomController {

    @Autowired
    RoomService roomService;

    /* Metodo para listar las habitaciones 
     * La linea de coneccion para el metodo get es: http://localhost:8081/hotelMate/v1/rooms
    */
    @GetMapping()
    public ResponseEntity<?> getRooms(){
        Map<String, Object> res = new HashMap<>();
        try{
            return ResponseEntity.ok().body(roomService.listHabitaciones());
        }catch(CannotCreateTransactionException err){
            res.put("message", "Error al conectar a la Base de Datos");
            res.put("Error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        }catch(DataAccessException err){
            res.put("message", "Error al consultar con la base de datos");
            res.put("Error",err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        }catch(Exception err){
            res.put("message", "Error general al obtener los datos");
            res.put("Error",err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }
        
    /*Metodo para crear habitaciones
     *La linea de coneccion para crear habitaciones es: http://localhost:8081/hotelMate/v1/rooms/assignment
     */
     @PostMapping("/assignment")
     public ResponseEntity<?> register(
        @Valid @ModelAttribute RoomsAssignmentDTO rooms,
        BindingResult result
     ){
        Map<String,Object> res = new HashMap<>();
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
            .stream()
            .map(error -> error.getDefaultMessage())
            .collect(Collectors.toList());
            res.put("message", "Error con las validaciones favor ingresar todos los campos");
            res.put("Errores", errors);
            return ResponseEntity.badRequest().body(res);
        }
        try{
            Long id = null;
            Rooms newRoom = new Rooms(
                id,
                rooms.getRoomNumber(),
                rooms.getNightPrice(),
                rooms.getDayPrice(),
                rooms.getRoomType(),
                rooms.getRoomCapacity()
            );
            roomService.register(newRoom);
            res.put("message", "Habitacion agregada correctamente");
            return ResponseEntity.ok(res);
        }catch(Exception err){
            res.put("message", "Error al guardar la habitacion, intente de nuevo");
            res.put("error",err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
     }   
   

    
}
