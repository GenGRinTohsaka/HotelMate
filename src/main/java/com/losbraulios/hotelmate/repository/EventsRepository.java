package com.losbraulios.hotelmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.losbraulios.hotelmate.models.Events;
import com.losbraulios.hotelmate.models.Services;

public interface EventsRepository extends JpaRepository<Events, Long>{

    List<Events> findByServices(Services services);
}

