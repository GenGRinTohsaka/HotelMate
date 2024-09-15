package com.losbraulios.hotelmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.losbraulios.hotelmate.models.Services;

public interface ServicesRepository extends JpaRepository<Services, Long>{
    
}
