package com.losbraulios.hotelmate.DTO;

import lombok.Data;

@Data
public class RoomsUpdateDTO {

    private Long roomId;
    private Long roomNumber;
    private Double nightPrice;
    private Double dayPrice;
    private String roomType;
    private String roomCapacity;
}
