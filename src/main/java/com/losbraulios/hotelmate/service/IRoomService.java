package com.losbraulios.hotelmate.service;

import java.util.List;

import com.losbraulios.hotelmate.models.Rooms;

public interface IRoomService {
    List<Rooms> listRooms();

    Rooms getRooms(Long idRoom); 
    Rooms register(Rooms room);
    void eliminate(Rooms room);

    

}