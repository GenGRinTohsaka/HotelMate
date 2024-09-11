package com.losbraulios.hotelmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.losbraulios.hotelmate.models.Employees;

public interface EmployeesRepository extends JpaRepository<Employees, Long>{

}
