package com.losbraulios.hotelmate.service;

import java.util.List;

import com.losbraulios.hotelmate.models.Habitaciones;

public interface IHabitacionService {
    List<Habitaciones> listHabitaciones();

    Habitaciones gHabitaciones(Long idHabitacion); 
    Habitaciones register(Habitaciones habitacion);
    void eliminate(Habitaciones habitacion);

    

}