package com.losbraulios.hotelmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.losbraulios.hotelmate.models.Clients;

public interface ClientsRepository extends JpaRepository<Clients, Long>{
    public Clients findByClients(Clients clients);
}
