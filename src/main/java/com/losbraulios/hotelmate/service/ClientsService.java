package com.losbraulios.hotelmate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losbraulios.hotelmate.models.Clients;
import com.losbraulios.hotelmate.repository.ClientsRepository;
import com.losbraulios.hotelmate.service.IService.IClientsService;

@Service
public class ClientsService implements IClientsService{
    @Autowired
    ClientsRepository clientsRepository;

    @Override
    public List<Clients> listClients() {
        return clientsRepository.findAll();
    }

    @Override
    public Clients getClients(Long idClient) {
        return clientsRepository.findById(idClient).orElse(null);
    }

    @Override
    public Clients register(Clients clients) {
        return clientsRepository.save(clients);
    }

    @Override
    public void eliminate(Clients clients) {
        clientsRepository.delete(clients);
    }
}