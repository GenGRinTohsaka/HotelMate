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

    /*
     * Esta funcion se encarga de registrar los eventos
     * El link de esta función es: http://localhost:8081/hotelMate/v1/events/save
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveEvent(
        @Valid @ModelAttribute EventsSaveDTO eventDTO,
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

    /*
     * Esta funcion se encarga de devolver un evento por medio de su ID
     * El link de esta función es: http://localhost:8081/hotelMate/v1/events/search/{eventId}
     */
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
            res.put("message", "Error general al obtener el evento buscado");
            res.put("error", e);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    /*
     * Esta funcion se encarga de eliminar un evento por medio de su ID
     * El link de esta función es: http://localhost:8081/hotelMate/v1/events/delete/{eventId}
     */
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
            res.put("message", "Error general al cancelar el evento");
            res.put("error", e);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    
    /*
     * Esta funcion se encarga de actualizar un evento el cual se busca por medio de su ID
     * El link de esta función es: http://localhost:8081/hotelMate/v1/events/update/{eventId}
     */
    @PutMapping("/update/{eventId}")
    public ResponseEntity<?> updateMyEvent(@PathVariable Long eventId, @RequestBody EventsSaveDTO newEvents){
        Map<String, Object> res = new HashMap<>();
        try {
            Events oldEvents = eventsService.findByIdEvents(eventId);
            Services services = serviceService.findFieldById(oldEvents.getServices().getServiceId());
            if (oldEvents == null) {
                res.put("message","No se encontro un evento con el ID " + eventId);
                return ResponseEntity.status(404).body(res);
            }
            if (newEvents.getEventName() == null) {
                newEvents.setEventName(oldEvents.getEventName());
            }
            if (newEvents.getEventDescription() == null) {
                newEvents.setEventDescription(oldEvents.getEventDescription());
            }
            if (newEvents.getServiceId() == null) {
                newEvents.setServiceId(services.getServiceId());
            } if(newEvents.getEndDate() == null){
                newEvents.setEndDate(oldEvents.getEndDate().toLocalDateTime());
            } if(newEvents.getStartDate() == null){
                newEvents.setStartDate(oldEvents.getStartDate().toLocalDateTime());
            } if(newEvents.getEventId() == null){
                newEvents.setEventId(eventId);
            }
            eventsService.save(newEvents);
            return ResponseEntity.ok().body(newEvents);
        } catch (CannotCreateTransactionException e) {
            res.put("message", "Error al conectar con la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException e) {
            res.put("message", "Error al actualizar el evento en la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception e) {
            res.put("message", "Error general al actualizar el evento");
            res.put("error", e);
            return ResponseEntity.internalServerError().body(res);
        }

    }
}
