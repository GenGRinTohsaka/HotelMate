package com.losbraulios.hotelmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.losbraulios.hotelmate.models.Reports;

public interface  ReportRepository extends JpaRepository <Reports, Long>{
}
