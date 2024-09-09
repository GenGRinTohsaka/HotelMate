package com.losbraulios.hotelmate.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.losbraulios.hotelmate.service.HabitacionService;

@RestController
@RequestMapping("hotelMate/v1/rooms")
public class RoomController {

    @Autowired
    HabitacionService habitacionService;

    /*Metodo para listar las habitaciones*/
    @GetMapping()
    public ResponseEntity<?> getRooms(){
        Map<String, Object> res = new HashMap<>();
        try{
            return ResponseEntity.ok().body(habitacionService.listHabitaciones());
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

}
