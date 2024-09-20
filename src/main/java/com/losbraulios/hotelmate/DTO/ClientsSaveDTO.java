package com.losbraulios.hotelmate.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientsSaveDTO {
    @NotBlank(message = "No puede ir vació, espicificar elNIT")
    private String nit;
    @NotBlank(message = "El nombre del cliente no puede ir vacío")
    private String nameClient;
    @NotBlank(message = "El apellido del cliente no puede ir vacío")
    private String surnameClient;
    @Email
    @NotBlank(message = "El email del cliente no puede ir vacío")
    private String emailClint;
    @NotBlank(message = "El numero telefonico no puede ir vacio")
    private String phoneClient;
} 
