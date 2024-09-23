package com.losbraulios.hotelmate.service;
import java.util.List;

import com.losbraulios.hotelmate.DTO.response.EventsResponseDTO;
import com.losbraulios.hotelmate.DTO.save.EventsSaveDTO;
import com.losbraulios.hotelmate.models.Events;

public interface IEventsService {
    List<EventsResponseDTO> myEvents();

    Events findByIdEvents(Long eventId);

    Events save(EventsSaveDTO eventDTO);

    void eliminate(Events events);

}
