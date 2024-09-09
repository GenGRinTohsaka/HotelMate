package com.losbraulios.hotelmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.losbraulios.hotelmate.models.Habitaciones;

public interface  HabitacionRepository extends JpaRepository <Habitaciones, Long>{
    
}
