package com.losbraulios.hotelmate.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHotel;
    @NotBlank
    private String direction;
    @NotBlank
    private String nameHotel;
    @NotBlank
    private String phone;
    @NotBlank
    private String category;


     @OneToMany(mappedBy = "hotel")
    private List<Rooms> rooms;  

}