package com.losbraulios.hotelmate.controllers;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;

import com.losbraulios.hotelmate.DTO.UserLoginDTO;
import com.losbraulios.hotelmate.DTO.UserSaveDTO;
import com.losbraulios.hotelmate.models.Users;
import com.losbraulios.hotelmate.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("hotelMate/v1/users")
public class UserController {

    @Autowired 
    UserService userService;

    @GetMapping()
    public ResponseEntity<?> getUsers() {
        Map<String, Object> res = new HashMap<>();
        try {
            return ResponseEntity.ok().body(userService.listUsers());
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

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(
            @Valid @ModelAttribute UserSaveDTO user,
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
            Users users = new Users(
                user.getIdUser(),
                user.getNameUser(),
                user.getPasswordUser(),
                user.getEmailUser()
            );
            userService.register(users);
            res.put("message", "Usuario creado correctamente");
            return ResponseEntity.ok(res);
        } catch (Exception err) {
            res.put("message", "Error al guardar el usuario, intente de nuevo");
            res.put("error", err.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO user) {
        Map<String, Object> res = new HashMap<>();
        try {
            System.out.println(user.getPasswordUser());
            if(userService.login(user.getNameUser(), user.getPasswordUser())){
                res.put("message", "Usuario logeado satisfactoriamente");
                return ResponseEntity.ok(res);
            }else{
                res.put("message", "Credenciales inválidas");
                return ResponseEntity.status(401).body(res);
            }
        } catch (Exception err) {
            res.put("message", "Error general al iniciar sesión");
            res.put("error", err);
            return ResponseEntity.internalServerError().body(res);
        }
    }
}
