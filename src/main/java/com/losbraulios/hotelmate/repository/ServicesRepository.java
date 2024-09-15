package com.losbraulios.hotelmate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.losbraulios.hotelmate.models.Services;

public interface ServicesRepository extends JpaRepository<Services, Long>{
    //Metodo para devolver Servicios de Un evento
    List<Services> findByServices(Services services);
}
