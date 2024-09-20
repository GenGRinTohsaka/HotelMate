package com.losbraulios.hotelmate.service;

import java.util.List;

import com.losbraulios.hotelmate.DTO.BillResponseDTO;
import com.losbraulios.hotelmate.DTO.BillSaveDTO;
import com.losbraulios.hotelmate.models.Bills;

public interface IBillService {
    
     //Método para listar facturas 
    List<BillResponseDTO> allBills(Long clientelId);

     //Método para mostrar solo 1 factura por su Id
     Bills findFieldById(Long id);

    //Método para guardar una factura
     Bills save(BillSaveDTO billDTO);

    //Metodo para eliminar una factura  
     void eliminate(Bills bill);

}
