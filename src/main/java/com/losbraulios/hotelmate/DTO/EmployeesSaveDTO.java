package com.losbraulios.hotelmate.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeesSaveDTO {
    @NotBlank(message = "El nombre del empleado no puede ir vacio")
    private String nameEmployee;
    @NotBlank(message = "El apellido del empleado no puede ir vacío")
    private String surnameEmployee;
    @NotBlank(message = "El numero telefonico del empleado no puede ir vacío")
    private String phoneEmployee;
    @Email
    @NotBlank(message = "El email del empleado no puede ir vacio")
    private String emailEmployee;
    @NotBlank(message = "El rol del empleado no puede ir vacío")
    private String roleEmployee;
}
