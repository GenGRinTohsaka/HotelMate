package com.losbraulios.hotelmate.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDTO {
    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    private String nameUser;
    @NotBlank(message = "La contraseña no puede estar vacia") 
    private String passwordUser; 
}
