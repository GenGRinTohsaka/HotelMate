package com.losbraulios.hotelmate.service;

import java.util.List;
import com.losbraulios.hotelmate.models.Hotel;

public interface IHotelService {
    List<Hotel> listHotel();

    Hotel getHotel(Long idHotel);

    Hotel register(Hotel hotel);

    void eliminate(Hotel hotel);
}
