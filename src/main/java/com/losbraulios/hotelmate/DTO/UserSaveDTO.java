package com.losbraulios.hotelmate.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserSaveDTO {

    private Long idUser;
    @NotBlank
    private String nameUser;
    @Email  
    @NotBlank
    @Column(unique = true)
    private String emailUser;
    @NotBlank
    private String passwordUser;
}
