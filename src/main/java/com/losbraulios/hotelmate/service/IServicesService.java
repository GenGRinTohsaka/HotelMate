package com.losbraulios.hotelmate.service;

import java.util.List;
import com.losbraulios.hotelmate.models.Services;

public interface IServicesService {
    List<Services> listServices();

    Services getServices(Long serviceId);

    Services register(Services services);

    void eliminate(Services services);
}
