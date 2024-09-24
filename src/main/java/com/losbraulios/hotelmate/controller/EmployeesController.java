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

import com.losbraulios.hotelmate.DTO.save.EmployeesSaveDTO;
import com.losbraulios.hotelmate.models.Employees;
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

    /*
     * Función que nos permite buscar empleado por su ID
     * http://localhost:8081/hotelMate/v1/employees/search/{idEmployee}
     */
    @GetMapping("/search/{idEmployee}")
    public ResponseEntity<?> searchEmployee(@PathVariable Long idEmployee) {
        Map<String, Object> res = new HashMap<>();
        try {
                Employees employees = employeesService.getEmployees(idEmployee);
            if (employees != null) {
                return ResponseEntity.ok().body(employees);
            } else {
                res.put("message", "Empleado no encontrado");
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
            res.put("message", "Error general al obtener el Empleado");
            res.put("error", e);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    /*
     * Función que permite eliminar un Empleado por medio de su ID
     * http://localhost:8081/hotelMate/v1/employees/delete/{idEmployee}
     */
    @DeleteMapping("/delete/{idEmployee}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long idEmployee) {
        Map<String, Object> res = new HashMap<>();
        try {
            Employees employees = employeesService.getEmployees(idEmployee);
            if (employees != null) {
                employeesService.eliminate(employees);
                res.put("message", "Empleado eliminado con éxito");
                return ResponseEntity.ok().body(res);
            } else {
                res.put("message", "No se encontró un Empleado con ID: " + idEmployee);
                return ResponseEntity.status(404).body(res);
            }
        } catch (CannotCreateTransactionException e) {
            res.put("message", "Error al conectar con la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException e) {
            res.put("message", "Error al eliminar al Empleado de la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception e) {
            res.put("message", "Error general al eliminar al Empleado");
            res.put("error", e);
            return ResponseEntity.internalServerError().body(res);
        }
    }

    /*
     * Función para actualizar datos de un Empleado por medio de su ID
     * http://localhost:8081/hotelMate/v1/employees/update/{idEmployee}
     */
    @PutMapping("/update/{idEmployee}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long idEmployee, @RequestBody Employees newWEmployee) {
        Map<String, Object> res = new HashMap<>();
        try {
            Employees oldEmployee = employeesService.getEmployees(idEmployee);
            if (oldEmployee == null) {
                res.put("message", "No se encontró al Empleado con ID: " + idEmployee);
                return ResponseEntity.status(404).body(res);
            }            
            //Todas estas validaciones son para que los datos anteriores tomen lugar de los valores null en el RequestBody
            if (newWEmployee.getNameEmployee() != null) {
                oldEmployee.setNameEmployee(newWEmployee.getNameEmployee());
            }
            if (newWEmployee.getSurnameEmployee() != null) {
                oldEmployee.setSurnameEmployee(newWEmployee.getSurnameEmployee());
            }
            if (newWEmployee.getPhoneEmployee() != null) {
                oldEmployee.setPhoneEmployee(newWEmployee.getPhoneEmployee());
            }
            if (newWEmployee.getEmailEmployee() != null) {
                oldEmployee.setEmailEmployee(newWEmployee.getEmailEmployee());
            }
            if (newWEmployee.getRoleEmployee() != null) {
                oldEmployee.setRoleEmployee(newWEmployee.getRoleEmployee());
            }

            employeesService.register(oldEmployee);
            return ResponseEntity.ok().body(oldEmployee);

        } catch (CannotCreateTransactionException e) {
            res.put("message", "Error al conectar con la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (DataAccessException e) {
            res.put("message", "Error al actualizar datos del Empleado en la base de datos");
            res.put("error", e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(503).body(res);
        } catch (Exception e) {
            res.put("message", "Error general al actualizar datos del Empleado");
            res.put("error", e);
            return ResponseEntity.internalServerError().body(res);
        }
    }

}
