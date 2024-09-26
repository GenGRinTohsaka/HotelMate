package com.losbraulios.hotelmate.models;


import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Reservations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReservation;
    @NotBlank
    private String descriptionReservation;
    @NotNull
    @FutureOrPresent
    private Timestamp starDate;
    @NotNull
    @FutureOrPresent
    private Timestamp endDate;
    @NotNull
    @ManyToOne
    private Rooms room;
    @NotNull
    @ManyToOne
    private Clients clients;
}
