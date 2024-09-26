package com.losbraulios.hotelmate.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losbraulios.hotelmate.DTO.response.ClientsResponseDTO;
import com.losbraulios.hotelmate.DTO.save.ClientsSaveDTO;
import com.losbraulios.hotelmate.models.Clients;
import com.losbraulios.hotelmate.models.Users;
import com.losbraulios.hotelmate.repository.ClientsRepository;
import com.losbraulios.hotelmate.service.IService.IClientsService;

@Service
public class ClientService implements IClientsService{
    @Autowired
    ClientsRepository clientRepository;

    @Autowired
    UserService userService;

    @Override
    public List<ClientsResponseDTO> listClients() {
        List<Clients> clients = clientRepository.findAll();
        return clients.stream().map(client -> new ClientsResponseDTO(client.getIdClient(),
        client.getNit(),
        client.getNameClient(),
        client.getSurnameClient(),
        client.getEmailClient(),
        client.getPhoneClient(),
        client.getUsers())).collect(Collectors.toList());

    }

    @Override
    public Clients findByIdClient(Long idClient) {
        return clientRepository.findById(idClient).orElse(null);
    }

    @Override
    public Clients register(ClientsSaveDTO clientDTO) {
        Users user = userService.getUser(clientDTO.getUserId());
        Clients clients = new Clients(
            clientDTO.getIdClient(),
            clientDTO.getNit(),
            clientDTO.getNameClient(),
            clientDTO.getSurnameClient(),
            clientDTO.getEmailClient(),
            clientDTO.getPhoneClient(),
            user
        );
        return clientRepository.save(clients);
    }

    @Override
    public void eliminate(Clients clients) {
        clientRepository.delete(clients);
    }

}