package com.losbraulios.hotelmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.losbraulios.hotelmate.models.Rooms;

public interface  HabitacionRepository extends JpaRepository <Rooms, Long>{
    
}
