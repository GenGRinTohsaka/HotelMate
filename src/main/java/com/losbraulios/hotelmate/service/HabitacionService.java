package com.losbraulios.hotelmate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losbraulios.hotelmate.models.Habitaciones;
import com.losbraulios.hotelmate.repository.HabitacionRepository;

@Service
public class HabitacionService implements IHabitacionService{
    @Autowired
    HabitacionRepository habitacionRepository;

    @Override
    public List<Habitaciones> listHabitaciones() {
        return habitacionRepository.findAll();
    }

    @Override
    public Habitaciones gHabitaciones(Long idHabitacion) { // Debe coincidir con la interfaz
        return habitacionRepository.findById(idHabitacion).orElse(null);
    }

    @Override
    public Habitaciones register(Habitaciones habitacion) { // Agregada llave de apertura
        return habitacionRepository.save(habitacion);
    }

    @Override
    public void eliminate(Habitaciones habitacion) {
        habitacionRepository.delete(habitacion);
    }
}