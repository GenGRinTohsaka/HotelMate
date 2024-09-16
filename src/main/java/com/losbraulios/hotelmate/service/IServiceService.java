package com.losbraulios.hotelmate.service;

import java.util.List;

import com.losbraulios.hotelmate.models.Services;

public interface IServiceService {
    List<Services> listServices();
    Services findFieldById(Long id);
    Services save(Services service);
    void eliminate(Services service);

}