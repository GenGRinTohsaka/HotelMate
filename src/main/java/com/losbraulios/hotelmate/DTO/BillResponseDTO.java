package com.losbraulios.hotelmate.DTO;

import com.losbraulios.hotelmate.models.Clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillResponseDTO {

    private Long idBill;
    private Double totalBill;
    private String paymentMethod;
    private Clients clients;
}
