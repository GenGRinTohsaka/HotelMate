package com.losbraulios.hotelmate.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.losbraulios.hotelmate.models.Bills;
import com.losbraulios.hotelmate.models.Clients;


public interface BillRepository extends JpaRepository<Bills, Long>{
    List<Bills> findByClient(Clients client);
}
