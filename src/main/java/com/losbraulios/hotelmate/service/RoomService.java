package com.losbraulios.hotelmate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losbraulios.hotelmate.models.Room;
import com.losbraulios.hotelmate.repository.RoomRepository;

@Service
public class RoomService implements IRoomService{
    @Autowired
    RoomRepository roomRepository;

    @Override
    public List<Room> listRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRooms(Long idRooms) { // Debe coincidir con la interfaz
        return roomRepository.findById(idRooms).orElse(null);
    }

    @Override
    public Room register(Room room) { // Agregada llave de apertura
        return roomRepository.save(room);
    }

    @Override
    public void eliminate(Room room) {
        roomRepository.delete(room);
    }
}