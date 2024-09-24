// carpeta de Iservice
package com.losbraulios.hotelmate.service.IService;

import java.util.List;

import com.losbraulios.hotelmate.models.Clients;

public interface IClientsService {
    List<Clients> listClients();

    Clients getClients(Long idClient);

    Clients register(Clients clients);

    void eliminate(Clients clients);
}
