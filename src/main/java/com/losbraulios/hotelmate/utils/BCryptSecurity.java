package com.losbraulios.hotelmate.utils;

import org.springframework.stereotype.Component;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Component
public class BCryptSecurity {
    //Función que encrypta la clave, esto talvez no se use ahora, pero cuando tengamos la clase usuarios servira
    public String encodePassword(String password) {
        return  BCrypt.withDefaults().hashToString(11, password.toCharArray());
    }

    //Función que determina sí existe
    public boolean checkPassword(String password, String hashedPassword){
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        return result.verified;
    }
}
