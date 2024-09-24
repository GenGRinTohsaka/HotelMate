package com.losbraulios.hotelmate.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserSaveDTO {

    private Long idUser;
    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    private String nameUser;
    @NotBlank (message = "La contraseña no puede estar vacia")
    private String passwordUser;
    @Email  
    @NotBlank(message = "El correo no puede ir vacio")
    @Column(unique = true)
    private String emailUser;
}
