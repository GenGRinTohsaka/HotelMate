package com.losbraulios.hotelmate.service.IService;

import java.util.List;

import com.losbraulios.hotelmate.models.Users;

public interface  IUserService {
    List<Users> listUsers();

    Users getUser(Long idUser);

    Users register(Users user);

    void eliminate(Users user);

    boolean login(String emailUser, String password);

    Users getUserByEmail(String emailUser);
}