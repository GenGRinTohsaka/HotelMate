package com.losbraulios.hotelmate.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.losbraulios.hotelmate.service.EventsService;

@RestController
@RequestMapping("hotelMate/v1/events")
public class EventsController {
    @Autowired
    EventsService eventsService;

    @GetMapping()
    public ResponseEntity<?> getEvents(){
        Map<String, Object> res = new HashMap<>();
        try {
            return ResponseEntity.ok().body(eventsService.myEvents());
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
}
