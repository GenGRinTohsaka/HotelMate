package com.losbraulios.hotelmate.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDTO {
    private String nameUser;
    @NotBlank(message = "La contraseña no puede estar vacia") 
    private String passwordUser; 
    @Email(message= "Tiene que contener el formato correcto de un correo")
    private String emailUser;
}
