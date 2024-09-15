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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.losbraulios.hotelmate.DTO.ServiceSaveDTO;
import com.losbraulios.hotelmate.models.Services;
import com.losbraulios.hotelmate.service.ServiceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("hotelMate/v1/services")
public class ServiceController {

    @Autowired
    ServiceService serviceService;

    /*
     * Esta funcion se encarga de devolver todos los servicios creados
     * El link de esta función es: http://localhost:8081/hotelMate/v1/services
     */
    @GetMapping()
    public ResponseEntity<?> getServices() {
        Map<String, Object> res = new HashMap<>();
        try {
            return ResponseEntity.ok().body(serviceService.listServices());
        } catch (CannotCreateTransactionException err) {
            res.put("message", "Error al conectar a la base de datos");
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

    /*Funcion que se encarga de crear un nuevo servicio
     *El link de la funcion es: http://localhost:8081/hotelMate/v1/services/save
    */
    @PostMapping("/save")
    public ResponseEntity<?> saveService(
            @Valid @ModelAttribute ServiceSaveDTO service,
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
        }try {
            Services services = new Services(
                null,
                service.getServiceName(),
                service.getServiceDescription()
            );
            serviceService.save(services);
            res.put("message", "Servicio creado correctamente");
            return ResponseEntity.ok(res);
        } catch (Exception err) {
            res.put("message", "Error al guardar el servicio, intente de nuevo");
            res.put("error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    /*Funcion encargada de devolver un servicio en base a su id
     *El link de la funcion es: http://localhost:8081/hotelMate/v1/services/search/{serviceId}
    */
    @GetMapping("/search/{serviceId}")
    public ResponseEntity<?> searchService(@PathVariable Long serviceId) {
        Map<String, Object> res = new HashMap<>();
        try {
            Services services = serviceService.findFieldById(serviceId);
            if (services != null) {
                return ResponseEntity.ok().body(services);
            } else {
                res.put("message", "Servicio no encontrado");
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
            res.put("message", "Error general al obtener el servicio");
            res.put("error", err);
            return ResponseEntity.internalServerError().body(res);
        }
    }


}
