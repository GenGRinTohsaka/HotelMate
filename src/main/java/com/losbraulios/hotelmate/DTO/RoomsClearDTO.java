package com.losbraulios.hotelmate.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomsClearDTO {
    private long roomNumber;
    private String roomType;
    private String roomCapacity;
}
