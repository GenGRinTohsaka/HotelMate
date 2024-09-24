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
import org.springframework.web.bind.annotation.RestController;

import com.losbraulios.hotelmate.DTO.save.EmployeesSaveDTO;
import com.losbraulios.hotelmate.DTO.save.HotelRegisterDTO;
import com.losbraulios.hotelmate.models.Employees;
import com.losbraulios.hotelmate.models.Hotel;
import com.losbraulios.hotelmate.service.EmployeesService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("hotelMate/v1/employees")
public class EmployeesController {

    @Autowired
    EmployeesService employeesService;

    /*
     * Método listar empleados
     * http://localhost:8081/hotelMate/v1/employees
     */
    @GetMapping()
    public ResponseEntity<?> getEmployees(){
        Map<String, Object> res = new HashMap<>();
        try {
            return ResponseEntity.ok().body(employeesService.listEmployees());
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
     * Función que permite agregar a empleados
     * http://localhost:8081/hotelMate/v1/employees/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(
        @Valid @ModelAttribute EmployeesSaveDTO employeeDTO,
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
            Long idEmployee = null;
            Employees newEmployees = new Employees(
                idEmployee,
                employeeDTO.getNameEmployee(),
                employeeDTO.getSurnameEmployee(),
                employeeDTO.getPhoneEmployee(),
                employeeDTO.getEmailEmployee(),
                employeeDTO.getRoleEmployee()
            );
            employeesService.register(newEmployees);
            res.put("message","Empleado agregado correctamente");
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("message", "Error al agregar Empleado, intente de nuevo mas tarde");
            res.put("Error", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

}
