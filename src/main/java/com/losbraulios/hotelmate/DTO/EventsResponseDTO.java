package com.losbraulios.hotelmate.DTO;

import java.sql.Timestamp;
import com.losbraulios.hotelmate.models.Services;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventsResponseDTO {
    private Long eventId;
    private String eventName;
    private String eventDescription;
    private Timestamp startDate;
    private Timestamp endDate;
    private Services serviceId;
}
