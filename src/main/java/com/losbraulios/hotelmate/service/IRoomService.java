package com.losbraulios.hotelmate.service;

import java.util.List;

import com.losbraulios.hotelmate.models.Room;

public interface IRoomService {
    List<Room> listRooms();

    Room getRooms(Long idRoom); 
    Room register(Room room);
    void eliminate(Room room);

    

}