package com.losbraulios.hotelmate.DTO.response;

import com.losbraulios.hotelmate.models.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientsResponseDTO {
    private Long idClient;
    private String nit;
    private String nameClient;
    private String surnameClient;
    private String emailClint;
    private String phoneClient;
    private Users users;
}
