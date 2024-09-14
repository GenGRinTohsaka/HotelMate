package com.losbraulios.hotelmate.service;

import java.util.List;

import com.losbraulios.hotelmate.DTO.RoomsAssignmentDTO;
import com.losbraulios.hotelmate.DTO.RoomsResponseDTO;
import com.losbraulios.hotelmate.models.Rooms;

public interface IRoomService {
  //Método para listar habitaciones 
  List<RoomsResponseDTO> myRooms(Long hotelId);

  //Método para mostrar solo 1 habitacion por su Id
  Rooms findByIdRoom(Long id);

  //Método para guardar una habitacion
  Rooms save(RoomsAssignmentDTO roomDTO);

  //Metodo para eliminar habitacion
  void eliminate(Rooms room);

}   