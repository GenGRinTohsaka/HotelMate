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
public class Employees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmployee;
    @NotBlank
    private String nameEmployee;
    @NotBlank
    private String surnameEmployee;
    @NotBlank
    private String phoneEmployee;
    @Email
    @NotBlank
    @Column(unique = true)
    private String emailEmployee;
    @NotBlank
    private String roleEmployee;

}
