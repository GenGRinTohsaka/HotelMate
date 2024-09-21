package com.losbraulios.hotelmate.service;

import java.util.List;

import com.losbraulios.hotelmate.models.Users;

public interface  IUserService {
    List<Users> listUsers();

    Users getUser(Long idUser);

    Users register(Users user);

    void eliminate(Users user);
}
