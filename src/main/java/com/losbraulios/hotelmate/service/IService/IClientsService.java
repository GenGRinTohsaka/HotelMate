package com.losbraulios.hotelmate.service.IService;

import java.util.List;

import com.losbraulios.hotelmate.DTO.response.ClientsResponseDTO;
import com.losbraulios.hotelmate.DTO.save.ClientsSaveDTO;
import com.losbraulios.hotelmate.models.Clients;

public interface IClientsService {
    List<ClientsResponseDTO> listClients();

    Clients findByIdClient(Long idClient);

    Clients register(ClientsSaveDTO clientDTO);

    void eliminate(Clients clients);
}
