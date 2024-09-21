package com.losbraulios.hotelmate.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientsClearDTO {
    private String nameClient;
    private String surnameClient;
    private String emailClint;
}