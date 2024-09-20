package com.losbraulios.hotelmate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losbraulios.hotelmate.models.Clients;
import com.losbraulios.hotelmate.repository.ClientsRepository;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getClients'");
    }

    @Override
    public Clients register(Clients clients) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'register'");
    }

    @Override
    public void eliminate(Clients clients) {
        clientsRepository.delete(clients);
    }
}