package com.losbraulios.hotelmate.DTO;

import java.sql.Timestamp;
import java.time.LocalTime;

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
    private LocalTime startHour;
    private LocalTime endHour;
    private Timestamp startDate;
    private Timestamp endDate;
    private Services services;
}
