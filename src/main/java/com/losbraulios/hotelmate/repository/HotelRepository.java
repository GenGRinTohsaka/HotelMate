package com.losbraulios.hotelmate.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.losbraulios.hotelmate.models.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long>{

    Hotel findByNameHotel(String nameHotel);
}