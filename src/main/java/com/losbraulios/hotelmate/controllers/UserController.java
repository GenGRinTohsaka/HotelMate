package com.losbraulios.hotelmate.controllers;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

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
import java.util.HashMap;

import com.losbraulios.hotelmate.DTO.UserLoginDTO;
import com.losbraulios.hotelmate.DTO.UserPasswordUpdateDTO;
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
                null,
                user.getNameUser(),
                user.getEmailUser(),
                user.getPasswordUser()
                
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
            if(userService.login(user.getEmailUser(), user.getPasswordUser(),user.getNameUser())==true){
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

    @DeleteMapping("/delete/{idUser}")
    public ResponseEntity<?> deleteUser(@PathVariable Long idUser) {
        Map<String, Object> res = new HashMap<>();
        try {
            Users user = userService.getUser(idUser);
            if (user != null) {
                userService.eliminate(user);
                res.put("message", "Usuario eliminado con éxito");
                return ResponseEntity.ok().body(res);
            } else {
                res.put("message", "No se encontró el Usuario con ID: " + idUser);
                return ResponseEntity.status(404).body(res);
            }
        } catch (CannotCreateTransactionException err) {
            res.put("message", "Error al conectar con la base de datos");
            res.put("error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException err) {
            res.put("message", "Error al eliminar el usuario de la base de datos");
            res.put("error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception err) {
            res.put("message", "Error general al eliminar el Usuario");
            res.put("error", err);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @PutMapping("/passwordUpdate/{idUser}")
    public ResponseEntity<?> updatePassword(@PathVariable Long idUser,
                                            @RequestBody UserPasswordUpdateDTO newUser){
        Map<String, Object> res = new HashMap<>();
        try{
            Users oldUsers = userService.getUser(idUser);
            if(oldUsers == null) {
                res.put("message","No se encontro usuario con el id: "+idUser);
                return ResponseEntity.status(404).body(res);
            }
            if(userService.login(newUser.getEmailUser(),newUser.getPasswordUser(),null)==false){
                res.put("message", "No se encontro cuenta con las credenciales brindadas");
                return ResponseEntity.status(404).body(res);
            }

            if(newUser.getPasswordUser() == oldUsers.getPasswordUser()){
                res.put("message", "La contraseña es la misma que la actual");
                return ResponseEntity.badRequest().body(res);
            }
            oldUsers.setPasswordUser(newUser.getNewPassword());
            userService.register(oldUsers);
            return ResponseEntity.ok().body(oldUsers);
        }catch (CannotCreateTransactionException err) {
            res.put("message", "Error al conectar con la base de datos");
            res.put("error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException err) {
            res.put("message", "Error al actualizar la contraseña en la base de datos");
            res.put("error", err.getMessage().concat(err.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception err) {
            res.put("message", "Error general al actualizar la contraseña");
            res.put("error", err);
            return ResponseEntity.internalServerError().body(res);
        }
    }
}
