package com.losbraulios.hotelmate.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Habitaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idHabitacion;
    @NotBlank
    private long numeroHabitacion;
    @NotBlank
    private double precioNoche;
    @NotBlank
    private double precioDia;
    @NotBlank
    private String tipoHabitacion;
    @NotBlank
    private String capacidadHabitacion;

}
