package com.losbraulios.hotelmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.losbraulios.hotelmate.models.Bills;

public interface  BillRepository extends JpaRepository<Bills, Long>{
    
}
