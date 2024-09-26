package com.losbraulios.hotelmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.losbraulios.hotelmate.models.Users;


public interface  UserRepository extends JpaRepository<Users, Long>{
    public Users findByEmailUser(String emailUser);
    public Users findByNameUser(String nameUser);
}