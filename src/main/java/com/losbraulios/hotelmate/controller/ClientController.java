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

import com.losbraulios.hotelmate.DTO.save.ClientsSaveDTO;
import com.losbraulios.hotelmate.models.Clients;
import com.losbraulios.hotelmate.models.Users;
import com.losbraulios.hotelmate.service.ClientService;
import com.losbraulios.hotelmate.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("hotelMate/v1/clients")
public class ClientController {
    @Autowired
    ClientService clientService;

    @Autowired
    UserService userService;

    /*
     * Este metodo se encarga de devolver todos los clientes registrados
     * El link de esta función es: http://localhost:8081/hotelMate/v1/clients
     */
    @GetMapping()
    public ResponseEntity<?> getClients(){
        Map<String, Object> res = new HashMap<>();
        try {
            return ResponseEntity.ok().body(clientService.listClients());
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
     * Esta funcion se encarga de registrar a los clientes
     * El link de esta función es: http://localhost:8081/hotelMate/v1/clients/save
     */
    @PostMapping("/save")
    public ResponseEntity<?> registerCLient(
        @Valid @ModelAttribute ClientsSaveDTO clientDTO,
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
            Clients clients = clientService.register(clientDTO);
            res.put("message", "Cliente guardado exitosamente");
            res.put("Evento", clients);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("message", "Error al registrar al cliente, intente de nuevo más tarde");
            res.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    /*
     * Esta funcion se encarga de devolver un cliente por su ID
     * El link de esta función es: http://localhost:8081/hotelMate/v1/clients/search/{idClient}
     */
    @GetMapping("/search/{idClient}")
    public ResponseEntity<?> myClientById(@PathVariable Long idClient){
        Map<String, Object> res = new HashMap<>();
        try {
            Clients clients = clientService.findByIdClient(idClient);
            if (clients != null) {
                return ResponseEntity.ok().body(clients);
            } else {
                res.put("message", "Cliente no encontrado");
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
            res.put("message", "Error general al obtener el Cliente buscado");
            res.put("error", e);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    /*
     * Esta funcion se encarga de eliminar un cliente por medio de su ID
     * El link de esta función es: http://localhost:8081/hotelMate/v1/clients/delete/{idClient}
     */
    @DeleteMapping("/delete/{idClient}")
    public ResponseEntity<?> deleteClient(@PathVariable Long idClient){
        Map<String, Object> res = new HashMap<>();
        try {
            Clients clients = clientService.findByIdClient(idClient);
            if (clients != null) {
                clientService.eliminate(clients);
                res.put("message", "Cliente eliminado con exito");
                return ResponseEntity.ok().body(res);
            }else{
                res.put("message", "No se enconto un Cliente con el ID"+ idClient);
                return ResponseEntity.status(404).body(res);
            }
        } catch (CannotCreateTransactionException e) {
            res.put("message", "Error al conectar con la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException e) {
            res.put("message", "Error al eliminar al Cliente de la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception e) {
            res.put("message", "Error general al eliminar al Cliente");
            res.put("error", e);
            return ResponseEntity.internalServerError().body(res);
        }
    }


    /*
     * Esta funcion se encarga de actualizar un Cliente el cual se busca por medio de su ID
     * El link de esta función es: http://localhost:8081/hotelMate/v1/clients/update/{idClient}
     */
    @PutMapping("/update/{idClient}")
    public ResponseEntity<?> updateClient(@PathVariable Long idClient, @RequestBody ClientsSaveDTO newClient){
        Map<String, Object> res = new HashMap<>();
        try {
            Clients oldClient = clientService.findByIdClient(idClient);
            Users users = userService.getUser(oldClient.getUsers().getIdUser());
            if (oldClient == null) {
                res.put("message","No se encontro un Cliente con el ID " + idClient);
                return ResponseEntity.status(404).body(res);
            }
            if (newClient.getNameClient() == null) {
                newClient.setNameClient(oldClient.getNameClient());
            }
            if (newClient.getSurnameClient() == null) {
                newClient.setSurnameClient(oldClient.getSurnameClient());
            }
            if (newClient.getNit() == null) {
                newClient.setNit(oldClient.getNit());
            } if(newClient.getEmailClient() == null){
                newClient.setEmailClient(oldClient.getEmailClient());
            } if(newClient.getPhoneClient() == null){
                newClient.setPhoneClient(oldClient.getPhoneClient());
            }if(newClient.getUserId() == null) {
                newClient.setUserId(users.getIdUser());
            }if(newClient.getIdClient() == null){
                newClient.setUserId(idClient);
            }
            clientService.register(newClient);
            return ResponseEntity.ok().body(newClient);
        } catch (CannotCreateTransactionException e) {
            res.put("message", "Error al conectar con la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException e) {
            res.put("message", "Error al actualizar los datos del Cliente en la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception e) {
            res.put("message", "Error general al actualizar los datos del Cliente");
            res.put("error", e);
            return ResponseEntity.internalServerError().body(res);
        }

    }
}
