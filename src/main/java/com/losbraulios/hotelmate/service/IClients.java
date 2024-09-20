package com.losbraulios.hotelmate.service;

import java.util.List;

import com.losbraulios.hotelmate.models.Clients;

public interface IClients {
    List<Clients> listClients();

    Clients getClients(Long idClient);

    Clients register(Clients clients);

    void eliminate(Clients clients);
}
