package com.losbraulios.hotelmate.service;

import java.util.List;

import com.losbraulios.hotelmate.models.Bills;

public interface IBillService {
    // listar facturas
    List<Bills> listBills();
    // buscar factura por Id
    Bills findFieldById(Long id);
    // gardar UNA factura
    Bills save(Bills bill);
    // eliminar una Factura
    void eliminate(Bills bill);

}
