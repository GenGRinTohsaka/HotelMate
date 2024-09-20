package com.losbraulios.hotelmate.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Clients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClient;
    @NotBlank
    private String nit;
    @NotBlank
    private String nameClient;
    @NotBlank
    private String surnameClient;
    @Email
    @NotBlank
    @Column(unique = true)
    private String emailClint;
    @NotBlank
    private String phoneClient;

}
