package com.losbraulios.hotelmate.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.losbraulios.hotelmate.models.Clients;
import com.losbraulios.hotelmate.models.Reservations;
import com.losbraulios.hotelmate.models.Rooms;

public interface ReservationRepository extends JpaRepository<Reservations, Long>{
    List<Reservations> findByClients(Clients clients);

    List<Rooms> findByRooms(Rooms room);

}
