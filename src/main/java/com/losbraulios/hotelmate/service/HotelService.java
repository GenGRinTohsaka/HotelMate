package com.losbraulios.hotelmate.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.losbraulios.hotelmate.models.Hotel;
import com.losbraulios.hotelmate.repository.HotelRepository;

@Service
public class HotelService implements IHotelService{
    @Autowired
    HotelRepository hotelRepository;

    @Override
    public List<Hotel> listHotel(){
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotel(Long idHotel){
        return hotelRepository.findById(idHotel).orElse(null);
    }

    @Override
    public Hotel register(Hotel hotel){
        return hotelRepository.save(hotel);
    }

    @Override
    public void eliminate(Hotel hotel) {
        hotelRepository.delete(hotel);
    }
}