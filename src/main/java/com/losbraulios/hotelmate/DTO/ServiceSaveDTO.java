package com.losbraulios.hotelmate.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceSaveDTO {
    private Long serviceId;
    @NotNull(message = "El nombre del servicio no puede estar vacio")
    private String serviceName;
    @NotNull(message = "La descripcion no puede estar vacio")
    private String serviceDescription;
}
