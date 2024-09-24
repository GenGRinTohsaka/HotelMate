package com.losbraulios.hotelmate.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserPasswordUpdateDTO {
    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    private String nameUser;
    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    @Email
    @Column(unique = true)
    private String emailUser;
    @NotBlank (message = "La contraseña no puede estar vacia")
    private String passwordUser;
    @NotBlank (message = "La nueva contraseña no puede estar vacia")
    private String newPassword;
}
