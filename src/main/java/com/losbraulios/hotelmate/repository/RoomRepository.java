package com.losbraulios.hotelmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.losbraulios.hotelmate.models.Room;

public interface  RoomRepository extends JpaRepository <Room, Long>{
    
}
