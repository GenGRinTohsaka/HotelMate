package com.losbraulios.hotelmate.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {

    @Bean
        Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dgtilloiy",
            "api_key", "245197668773757",
            "api_secret", "lchNB2dTgUrflrTV8j3Y5fRhnXU"
        ));
    }
}
