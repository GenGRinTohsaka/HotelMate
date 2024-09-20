package com.losbraulios.hotelmate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losbraulios.hotelmate.models.Bills;
import com.losbraulios.hotelmate.repository.BillRepository;


@Service
public class BillService implements IBillService{
    
    @Autowired
    private BillRepository billRepository;

    @Override
    public List<Bills> listBills() {
        return billRepository.findAll();
    }

    @Override
    public Bills findFieldById(Long id) {
        return billRepository.findById(id).orElse(null);
    }

    @Override
    public Bills save(Bills bill) {
        return billRepository.save(bill);
    }

    @Override
    public void eliminate(Bills bill) {
        billRepository.delete(service);
    }
}
