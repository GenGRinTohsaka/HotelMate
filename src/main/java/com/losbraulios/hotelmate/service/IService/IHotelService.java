package com.losbraulios.hotelmate.service.IService;

import java.util.List;
import com.losbraulios.hotelmate.models.Hotel;

public interface IHotelService {
    List<Hotel> listHotel();

    Hotel getHotelByName(String hotelName);

    Hotel getHotel(Long idHotel);

    Hotel register(Hotel hotel);

    void eliminate(Hotel hotel);
}