// carpeta de Iservice
package com.losbraulios.hotelmate.service.IService;

import java.util.List;

import com.losbraulios.hotelmate.models.Reports;

public interface IReportService {
    List<Reports> listReports();

    Reports getReports(Long idReport);

    Reports register(Reports report);

    void eliminate(Reports report);
}
