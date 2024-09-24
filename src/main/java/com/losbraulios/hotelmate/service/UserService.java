package com.losbraulios.hotelmate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losbraulios.hotelmate.models.Users;
import com.losbraulios.hotelmate.repository.UserRepository;
// modelo de carpeta de Iservice
import com.losbraulios.hotelmate.service.IService.IUserService;

@Service
public class UserService implements IUserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Users> listUsers(){
        return userRepository.findAll();
    }

    @Override
    public Users getUser(Long idUsers){
        return userRepository.findById(idUsers).orElse(null);
    }

    @Override
    public Users register(Users user){
        return userRepository.save(user);
    }

    @Override
    public void eliminate(Users user) {
        userRepository.delete(user);
    }
}
