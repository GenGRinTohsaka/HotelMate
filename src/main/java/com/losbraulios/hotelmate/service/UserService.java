package com.losbraulios.hotelmate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losbraulios.hotelmate.models.Users;
import com.losbraulios.hotelmate.repository.UserRepository;
import com.losbraulios.hotelmate.service.IService.IUserService;
import com.losbraulios.hotelmate.utils.BCryptSecurity;

@Service
public class UserService implements IUserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptSecurity bCryptSecurity;

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
        if(user.getPasswordUser() != null){
            user.setPasswordUser(bCryptSecurity.encodePassword(user.getPasswordUser()));
        }
        return userRepository.save(user);
    }

    @Override
    public void eliminate(Users user) {
        userRepository.delete(user);
    }

    @Override
    public Users getUserByEmail(String emailUser){
        return  userRepository.findByEmailUser(emailUser);
    }
    @Override
    public boolean login(String emailUser, String password){
        Users user = getUserByEmail(emailUser);
        if(user == null || !bCryptSecurity.checkPassword(password, user.getPasswordUser())){
            return false;
        }
        return true;
    }
}