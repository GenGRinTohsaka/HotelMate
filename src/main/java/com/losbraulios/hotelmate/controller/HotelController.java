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

import com.losbraulios.hotelmate.DTO.HotelRegisterDTO;
import com.losbraulios.hotelmate.models.Hotel;
import com.losbraulios.hotelmate.service.HotelService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("hotelMate/v1/hotels")
public class HotelController {
    @Autowired
    HotelService hotelService;

    /*
     * Método listar 
     * http://localhost:8081/hotelMate/v1/hotels
     */
    @GetMapping()
    public ResponseEntity<?> getHotels(){
        Map<String, Object> res = new HashMap<>();
        try {
            return ResponseEntity.ok().body(hotelService.listHotel());
        }catch (CannotCreateTransactionException e){
            res.put("message", "Error al conectar con la base de datos");
            res.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        }catch(DataAccessException e){
            res.put("message", "Error al consultar con la base de datos");
            res.put("Error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception e) {
            res.put("message", "Error general al obtener los datos");
            res.put("Error", e);
            return ResponseEntity.internalServerError().body(res);
        } 
    }

    /*
     * Función que permite agregar a los hoteles
     * http://localhost:8081/hotelMate/v1/hotels/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerHotel(
        @Valid @ModelAttribute HotelRegisterDTO hotel,
        BindingResult result
    ){
        Map<String, Object> res = new HashMap<>();
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
                res.put("message", "Error con las validaciones, Ingresa todos los campos");
                res.put("Errors", errors);
                return ResponseEntity.badRequest().body(res);
        }
        try {           
            Long idHotel = null;
            Hotel newHotel = new Hotel(
                idHotel,
                hotel.getDirection(),
                hotel.getNameHotel(),
                hotel.getPhone(),
                hotel.getCategory()
            );
            hotelService.register(newHotel);
            res.put("message","Hotel agregado correctamente");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("message", "Error al agregar Hotel, intente de nuevo mas tarde");
            res.put("Error", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    /*
     * Función que nos permite buscar pot el ID a un hotel
     * http://localhost:8081/hotelMate/v1/hotels/search/{idHotel}
     */
    @GetMapping("/search/{idHotel}")
    public ResponseEntity<?> searchHotel(@PathVariable Long idHotel) {
        Map<String, Object> res = new HashMap<>();
        try {
            Hotel hotel = hotelService.getHotel(idHotel);
            if (hotel != null) {
                return ResponseEntity.ok().body(hotel);
            } else {
                res.put("message", "Hotel no encontrado");
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
            res.put("message", "Error general al obtener el hotel");
            res.put("error", e);
            return ResponseEntity.internalServerError().body(res);
        }
    }
    
    /*
     * Función que permite eliminar un hotel por medio de su ID
     * http://localhost:8081/hotelMate/v1/hotels/delete/{idHotel}
     */
    @DeleteMapping("/delete/{idHotel}")
    public ResponseEntity<?> deleteHotel(@PathVariable Long idHotel) {
        Map<String, Object> res = new HashMap<>();
        try {
            Hotel hotel = hotelService.getHotel(idHotel);
            if (hotel != null) {
                hotelService.eliminate(hotel);
                res.put("message", "Hotel eliminado con éxito");
                return ResponseEntity.ok().body(res);
            } else {
                res.put("message", "No se encontró el hotel con ID: " + idHotel);
                return ResponseEntity.status(404).body(res);
            }
        } catch (CannotCreateTransactionException e) {
            res.put("message", "Error al conectar con la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException e) {
            res.put("message", "Error al eliminar el hotel de la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception e) {
            res.put("message", "Error general al eliminar el hotel");
            res.put("error", e);
            return ResponseEntity.internalServerError().body(res);
        }
    }


    /*
     * Función para actualizar un Hotel por medio de su ID
     * http://localhost:8081/hotelMate/v1/hotels/update/{idHotel}
     */
    @PutMapping("/update/{idHotel}")
    public ResponseEntity<?> updateHotel(@PathVariable Long idHotel, @RequestBody Hotel oldHotel) {
        Map<String, Object> res = new HashMap<>();
        try {
            Hotel newHotel = hotelService.getHotel(idHotel);
            if (newHotel == null) {
                res.put("message", "No se encontró el hotel con ID: " + idHotel);
                return ResponseEntity.status(404).body(res);
            }            
            // Actualizar los campos del hotel existente
            newHotel.setDirection(oldHotel.getDirection());
            newHotel.setNameHotel(oldHotel.getNameHotel());
            newHotel.setPhone(oldHotel.getPhone());
            newHotel.setCategory(oldHotel.getCategory());

            hotelService.register(newHotel);
            return ResponseEntity.ok().body(newHotel);

        } catch (CannotCreateTransactionException e) {
            res.put("message", "Error al conectar con la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException e) {
            res.put("message", "Error al actualizar el hotel en la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception e) {
            res.put("message", "Error general al actualizar el hotel");
            res.put("error", e);
            return ResponseEntity.internalServerError().body(res);
        }
    }

}
