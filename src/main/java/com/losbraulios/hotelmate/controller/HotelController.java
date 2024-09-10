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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.losbraulios.hotelmate.DTO.HotelRegisterDTO;
import com.losbraulios.hotelmate.configs.CloudinaryConfig;
import com.losbraulios.hotelmate.service.CloudinaryService;
import com.losbraulios.hotelmate.service.HotelService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("hotelMate/v1/hotels")
public class HotelController {
    @Autowired
    HotelService hotelService;
    @Autowired
    CloudinaryService cloudinaryService;

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

    @PostMapping("/register")
    public ResponseEntity<?> registerHotel(
        @RequestPart("hotelPicture") MultipartFile hotelPicture,
        @Valid @ModelAttribute HotelRegisterDTO hotel,
        BindingResult result
    ){
        Map<String, Object> res = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
            .stream()
            .map(error -> error.getDefaultMessage())
            .collect(Collectors.toList());
            res.put("message", "Error con las validaciones, Ingresar todos los campos");
            res.put("Error",errors );
            return  ResponseEntity.badRequest().body(res);
        }
        try {           
        Map<String,Object> uploadResult = cloudinaryService
        String img = uploadResult.get("url").toString();
        

        } catch (Exception e) {

        }
    }
}
