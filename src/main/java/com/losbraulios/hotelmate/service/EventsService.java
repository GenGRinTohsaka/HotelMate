package com.losbraulios.hotelmate.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losbraulios.hotelmate.DTO.EventsResponseDTO;
import com.losbraulios.hotelmate.DTO.EventsSaveDTO;
import com.losbraulios.hotelmate.models.Events;
import com.losbraulios.hotelmate.models.Services;
import com.losbraulios.hotelmate.repository.EventsRepository;

@Service
public class EventsService implements IEventsService{
    @Autowired
    EventsRepository eventsRepository;

    @Autowired
    ServicesService servicesService;

    @Override
    public List<EventsResponseDTO> myEvents(Long serviceId) {
        Services services = servicesService.getServices(serviceId);
        List<Events> events = eventsRepository.findByServices(services);
        return events.stream().map(event -> new EventsResponseDTO(event.getEventId(),
        event.getEventName(),
        event.getEventDescription(),
        event.getStartHour(),
        event.getEndHour(),
        event.getStartDate(),
        event.getEndDate(),
        event.getServices())).collect(Collectors.toList());
    }

    @Override
    public Events findByIdEvents(Long eventId) {
        return eventsRepository.findById(eventId).orElse(null);
    }

    @Override
    public Events save(EventsSaveDTO eventDTO) {
       Services services = servicesService.getServices(eventDTO.getServiceId());
       Events event = new Events(
        eventDTO.getEventId(),
        eventDTO.getEventName(),
        eventDTO.getEventDescription(),
        eventDTO.getStartHour(),
        eventDTO.getEndHour(),
        eventDTO.getStartDate(),
        services
       );
       return eventsRepository.save(event);
    }

    @Override
    public void eliminate(Events events) {
        eventsRepository.delete(events);
    }
}