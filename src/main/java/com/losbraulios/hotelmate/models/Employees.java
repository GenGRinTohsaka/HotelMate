package com.losbraulios.hotelmate.models;

import jakarta.persistence.Column;
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
public class Employees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employeeId;
    @NotBlank
    private String employeeName;
    @NotBlank
    private String employeeLastName;
    @NotBlank
    private String employeePhone;
    @NotBlank
    @Column(unique = true)
    private String employeeEmail;
    @NotBlank
    private String employeeRole;

}
