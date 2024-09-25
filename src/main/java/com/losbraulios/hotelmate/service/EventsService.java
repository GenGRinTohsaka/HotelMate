package com.losbraulios.hotelmate.service;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.losbraulios.hotelmate.DTO.EventsResponseDTO;
import com.losbraulios.hotelmate.DTO.EventsSaveDTO;
import com.losbraulios.hotelmate.models.Events;
import com.losbraulios.hotelmate.models.Services;
import com.losbraulios.hotelmate.repository.EventsRepository;
import com.losbraulios.hotelmate.service.IService.IEventsService;


@Service
public class EventsService implements IEventsService{
    @Autowired
    EventsRepository eventsRepository;

    @Autowired
    ServicesService servicesService;


    @Override
    public List<EventsResponseDTO> myEvents() {
        List<Events> events = eventsRepository.findAll();
        return events.stream().map(event -> responseDTO(event)).collect(Collectors.toList());
    }

    @Override
    public Events findByIdEvents(Long eventId) {
        return eventsRepository.findById(eventId).orElse(null);
    }

    @Override
    public Events save(EventsSaveDTO eventDTO) {
      try{
            Timestamp startDate = Timestamp.valueOf(eventDTO.getStartDate());
            Timestamp endDate = Timestamp.valueOf(eventDTO.getEndDate());
            Services services = servicesService.getServices(eventDTO.getServiceId());
            Events event = new Events(
                eventDTO.getEventId(),
                eventDTO.getEventName(),
                eventDTO.getEventDescription(),
                eventDTO.getStartHour(),
                eventDTO.getEndHour(),
                startDate,
                endDate,
                services
            );
            return eventsRepository.save(event);
        }catch(Exception e){
            throw new IllegalArgumentException("Error al parsear las fechas", e);
        }
    }

    @Override
    public void eliminate(Events events) {
        eventsRepository.delete(events);
    }

    private EventsResponseDTO responseDTO(Events events){

        EventsResponseDTO dto = new EventsResponseDTO(
            events.getEventId(),
            events.getEventName(),
            events.getEventDescription(),
            events.getStartDate(),
            events.getEndDate(),
            events.getServices()
        );

        return dto;
    }
}
