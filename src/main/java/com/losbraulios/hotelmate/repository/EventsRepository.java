package com.losbraulios.hotelmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.losbraulios.hotelmate.models.Events;

public interface EventsRepository extends JpaRepository<Events, Long>{

    
}
