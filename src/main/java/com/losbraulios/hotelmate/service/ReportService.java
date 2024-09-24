package com.losbraulios.hotelmate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losbraulios.hotelmate.models.Reports;
import com.losbraulios.hotelmate.repository.ReportRepository;
// modelo de carpeta de Iservice
import com.losbraulios.hotelmate.service.IService.IReportService;

@Service
public class ReportService implements IReportService {
    
    @Autowired
    private ReportRepository reportRepository;

    @Override
    public List<Reports> listReports() {
        return reportRepository.findAll();
    }

    @Override
    public Reports getReports(Long idReport) {
        return reportRepository.findById(idReport).orElse(null);
    }

    @Override
    public Reports register(Reports report) {
        return reportRepository.save(report);
    }

    @Override
    public void eliminate(Reports report) {
        reportRepository.delete(report);
    }
}