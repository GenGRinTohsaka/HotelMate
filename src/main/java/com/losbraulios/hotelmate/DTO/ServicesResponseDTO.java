package com.losbraulios.hotelmate.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicesResponseDTO {

    private Long serviceId;
    private String serviceName;
    private String serviceDescription;
}
