package com.losbraulios.hotelmate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losbraulios.hotelmate.models.Services;
import com.losbraulios.hotelmate.repository.ServicesRepository;

@Service
public class ServiceService implements IServiceService {
    
    @Autowired
    private ServicesRepository servicesRepository;

    @Override
    public List<Services> listServices() {
        return servicesRepository.findAll();
    }

    @Override
    public Services findFieldById(Long id) {
        return servicesRepository.findById(id).orElse(null);
    }

    @Override
    public Services save(Services service) {
        return servicesRepository.save(service);
    }

    @Override
    public void eliminate(Services service) {
        servicesRepository.delete(service);
    }
}