package com.losbraulios.hotelmate.DTO;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BillSaveDTO {

    // atributos de Fcatura:
    private Long idBill;
    @NotNull(message = "El sera generado automaticamente")
    private Double totalBill;
    @NotNull(message = "El metodo de pago no puede ir vacio")
    private String paymentMethod;

    // id para guardar la factur a este Id
    @NotNull(message = "Aun no se ha selecionado un Cliente para emitir factura")
    private Long idCliente;
}
