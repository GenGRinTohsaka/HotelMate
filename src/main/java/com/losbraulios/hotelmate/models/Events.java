
package com.losbraulios.hotelmate.models;

import java.sql.Timestamp;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    @NotBlank
    private String eventName;
    @NotBlank
    private String eventDescription;
    @NotNull
    @FutureOrPresent
    private LocalTime startHour;
    @NotNull
    @FutureOrPresent
    private LocalTime endHour;
    @NotNull
    @FutureOrPresent
    private Timestamp startDate;
    @NotNull
    @FutureOrPresent
    private Timestamp endDate;
    @NotNull
    @OneToOne
    private Services services;
}
