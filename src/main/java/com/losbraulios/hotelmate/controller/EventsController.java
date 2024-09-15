package com.losbraulios.hotelmate.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.sql.Time;
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

import com.losbraulios.hotelmate.DTO.EventsSaveDTO;
import com.losbraulios.hotelmate.models.Events;
import com.losbraulios.hotelmate.models.Services;
import com.losbraulios.hotelmate.service.EventsService;
import com.losbraulios.hotelmate.service.ServiceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("hotelMate/v1/events")
public class EventsController {
    @Autowired
    EventsService eventsService;

    @Autowired
    ServiceService serviceService;

    /*
     * Esta funcion se encarga de devolver todos los eventos creados
     * El link de esta función es: http://localhost:8081/hotelMate/v1/events
     */
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

    @PostMapping("/save")
    public ResponseEntity<?> saveEvent(
        @Valid @ModelAttribute EventsSaveDTO eventDTO,
        BindingResult result
    ){
        Map<String, Object> res = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            res.put("message", "Error con las validaciones favor ingresar todos los campos");
            res.put("Errores", errors);
            return ResponseEntity.badRequest().body(res);       
        }try {
            Events events = eventsService.save(eventDTO);
            res.put("message", "Evento guardado exitosamente");
            res.put("Evento", events);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("message", "Error al registrar el evento, intente de nuevo más tarde");
            res.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @GetMapping("/search/{eventId}")
    public ResponseEntity<?> myEventById(@PathVariable Long eventId){
        Map<String, Object> res = new HashMap<>();
        try {
            Events events = eventsService.findByIdEvents(eventId);
            if (events != null) {
                return ResponseEntity.ok().body(events);
            } else {
                res.put("message", "Evento no encontrado");
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
            res.put("message", "Error general al obtener el servicio");
            res.put("error", e);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long eventId){
        Map<String, Object> res = new HashMap<>();
        try {
            Events events = eventsService.findByIdEvents(eventId);
            if (events != null) {
                eventsService.eliminate(events);
                res.put("message", "Evento cancelado con exito");
                return ResponseEntity.ok().body(res);
            }else{
                res.put("message", "No se enconto un evento con el ID"+ eventId);
                return ResponseEntity.status(404).body(res);
            }
        } catch (CannotCreateTransactionException e) {
            res.put("message", "Error al conectar con la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException e) {
            res.put("message", "Error al eliminar el servicio de la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception e) {
            res.put("message", "Error general al eliminar el servicio");
            res.put("error", e);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @PutMapping("/update/{eventId}")
    public ResponseEntity<?> updateMyEvent(@PathVariable Long eventId, @RequestBody EventsSaveDTO newEvents){
        Map<String, Object> res = new HashMap<>();
        try {
            Events oldEvents = eventsService.findByIdEvents(eventId);
            Services services = serviceService.findFieldById(newEvents.getServiceId());
            if (oldEvents != null) {
                res.put("message","No se encontro un evento con el ID" + eventId);
                return ResponseEntity.status(404).body(res);
            }

            oldEvents.setEventName(newEvents.getEventName());
            oldEvents.setEventDescription(newEvents.getEventDescription());
            oldEvents.setStartDate(Timestamp.valueOf(newEvents.getStartDate()));
            oldEvents.setEndDate(Timestamp.valueOf(newEvents.getEndDate()));
            oldEvents.setServices(services);

        // Validar que las fechas sean futuras o presentes
            LocalDateTime now = LocalDateTime.now();
            if (newEvents.getStartDate().isBefore(now) || newEvents.getEndDate().isBefore(now)) {
            res.put("message", "Las fechas de inicio y fin deben ser futuras o presentes");
            return ResponseEntity.status(400).body(res);
            }   

            eventsService.save(newEvents);
            return ResponseEntity.ok().body(newEvents);

        } catch (CannotCreateTransactionException e) {
            res.put("message", "Error al conectar con la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException e) {
            res.put("message", "Error al actualizar la habitacion en la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception e) {
            res.put("message", "Error general al actualizar la habitacion");
            res.put("error", e);
            return ResponseEntity.internalServerError().body(res);
        }

    }
}
