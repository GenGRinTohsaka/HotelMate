package com.losbraulios.hotelmate.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losbraulios.hotelmate.DTO.RoomsAssignmentDTO;
import com.losbraulios.hotelmate.DTO.RoomsResponseDTO;
import com.losbraulios.hotelmate.models.Hotel;
import com.losbraulios.hotelmate.models.Rooms;
import com.losbraulios.hotelmate.repository.RoomRepository;
// modelo de carpeta de Iservice
import com.losbraulios.hotelmate.service.IService.IRoomService;

@Service
public class RoomService implements IRoomService{
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    HotelService hotelService;

    // Método para listar habitaciones por hotel
    @Override
    public List<RoomsResponseDTO> myRooms(Long hotelId) {
        Hotel hotel = hotelService.getHotel(hotelId);
        List<Rooms> rooms = roomRepository.findByHotel(hotel);
        return rooms.stream()
            .map(room ->  new RoomsResponseDTO( room.getRoomId(),
            room.getRoomNumber(),
            room.getNightPrice(),
            room.getDayPrice(),
            room.getRoomType(),
            room.getRoomCapacity(),
            room.getHotel()))
            .collect(Collectors.toList());
        
        
    }

    // Método para encontrar una habitación por su ID
    @Override
    public Rooms findByIdRoom(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    // Método para guardar una nueva habitación
    @Override
    public Rooms save(RoomsAssignmentDTO roomDTO) {
        
        // Busca el hotel con el ID proporcionado en el DTO
        Hotel hotel = hotelService.getHotel(roomDTO.getIdHotel());
    
        // Verifica que el hotel exista
        
        
            // Crea una nueva instancia de Rooms con los datos del DTO y el hotel
            Rooms room = new Rooms(
                // id será null para una nueva entidad, será generado automáticamente
                roomDTO.getRoomId(), 
                roomDTO.getRoomNumber(),
                roomDTO.getNightPrice(),
                roomDTO.getDayPrice(),
                roomDTO.getRoomType(),
                roomDTO.getRoomCapacity(),
                hotel // Relaciona la habitación con el hotel
            );
    
            // Guarda la habitación en la base de datos y retorna la entidad guardada
            return roomRepository.save(room);
            
        
    }

    @Override
    public void eliminate(Rooms room) {
        roomRepository.delete(room);
    }
}