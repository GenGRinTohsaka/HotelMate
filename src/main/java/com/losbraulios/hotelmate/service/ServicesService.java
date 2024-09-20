package com.losbraulios.hotelmate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.losbraulios.hotelmate.models.Services;
import com.losbraulios.hotelmate.repository.ServicesRepository;
import com.losbraulios.hotelmate.service.IService.IServicesService;


@Service
public class ServicesService implements IServicesService{
    @Autowired
    ServicesRepository servicesRepository;

    @Override
    public List<Services> listServices() {
        return servicesRepository.findAll();
    }

    @Override
    public Services getServices(Long serviceId) {
        return servicesRepository.findById(serviceId).orElse(null);
    }

    @Override
    public Services register(Services services) {
        return servicesRepository.save(services);
    }

    @Override
    public void eliminate(Services services) {
        servicesRepository.delete(services);
    }

    public Services findFieldById(Long serviceId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findFieldById'");
    } 
}