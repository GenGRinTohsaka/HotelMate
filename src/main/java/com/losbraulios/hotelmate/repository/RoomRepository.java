package com.losbraulios.hotelmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.losbraulios.hotelmate.models.Rooms;

public interface  RoomRepository extends JpaRepository <Rooms, Long>{
    
}
