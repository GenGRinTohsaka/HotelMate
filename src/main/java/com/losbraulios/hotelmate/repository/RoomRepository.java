package com.losbraulios.hotelmate.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.losbraulios.hotelmate.models.Hotel;
import com.losbraulios.hotelmate.models.Rooms;

public interface  RoomRepository extends JpaRepository <Rooms, Long>{
      //Metodo personalizado que busqueda y devuelva todas las habitaciones de un hotel
        List<Rooms> findByHotel(Hotel hotel);
}
