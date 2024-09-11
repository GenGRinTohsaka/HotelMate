package com.losbraulios.hotelmate.service;

import java.util.List;
import com.losbraulios.hotelmate.models.Employees;

public interface IEmployeesService {
    List<Employees> listEmployees();

    Employees getEmployees(Long idEmployee);

    Employees register(Employees employees);

    void eliminate(Employees employees);
}
