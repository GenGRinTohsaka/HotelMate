package com.losbraulios.hotelmate.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.losbraulios.hotelmate.models.Employees;
import com.losbraulios.hotelmate.repository.EmployeesRepository;

@Service
public class EmployeesService implements IEmployeesService{
    @Autowired
    EmployeesRepository employeesRepository;

    @Override
    public List<Employees> listEmployees() {
        return employeesRepository.findAll();
    }

    @Override
    public Employees getEmployees(Long idEmployee) {
        return employeesRepository.findById(idEmployee).orElse(null);
    }

    @Override
    public Employees register(Employees employees) {
        return employeesRepository.save(employees);
    }

    @Override
    public void eliminate(Employees employees) {
        employeesRepository.delete(employees);
    }
}