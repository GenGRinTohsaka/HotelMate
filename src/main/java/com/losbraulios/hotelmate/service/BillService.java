package com.losbraulios.hotelmate.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.losbraulios.hotelmate.DTO.BillResponseDTO;
import com.losbraulios.hotelmate.DTO.BillSaveDTO;
import com.losbraulios.hotelmate.models.Bills;
import com.losbraulios.hotelmate.models.Clients;
import com.losbraulios.hotelmate.repository.BillRepository;
import com.losbraulios.hotelmate.service.IService.IBillService;


@Service
public class BillService implements IBillService{
    
    @Autowired
    // Repositorio de facturas para interactuar con la base de datos
    private BillRepository billRepository; 

    @Autowired
    // El Servicio para obtener la información del cliente
    private ClientsService clientService; 

    /**
     * Obtiene todas las facturas relacionada a un cliente 
     * El ID del cliente para buscar sus facturas
     * @param clientId 
     * Lista los objetos BillResponseDTO que contienen las facturas del cliente
     * @return 
     */
    
    @Override
    public List<BillResponseDTO> allBills(Long clientId) {
        // Obtiene el cliente a partir de su ID     
        Clients client = clientService.getClients(clientId);
        // Obtiene todas las facturas asociadas a ese cliente
        List<Bills> bills = billRepository.findByClient(client);
        // convierte una factura a un DTO de respuesta
        return bills.stream()
            .map(bill -> toResponseDTO(bill))
            .collect(Collectors.toList());
    }

    /**
     * Busca una factura por su ID
     * El ID de la factura a buscar
     * @param id 
     * El objeto Bills correspondiente si se encuentra, null si no existe
     * @return 
     */


    @Override
    public Bills findFieldById(Long id) {
        // Buscar una factura por su ID
        return billRepository.findById(id).orElse(null);
    }

    /**
     * Guarda una nueva factura en la base de datos asociada a un cliente
     * Un objeto BillSaveDTO con los datos de la factura a guardar
     * @param billDTO 
     * La factura guardada como un objeto Bills
     * @return 
     */


    @Override
    public Bills save(BillSaveDTO billDTO) {
        // Obtiene el cliente por su ID usando el servicio de clientes
        Clients client = clientService.getClients(billDTO.getIdCliente());
        

        // Hacce una factura nueva con los datos proporcionados
        Bills bill = new Bills();
        bill.setTotalBill(billDTO.getTotalBill());
        bill.setPaymentMethod(billDTO.getPaymentMethod());
        // Asignar el cliente a la factura
        bill.setClients(client);

        // Guardar la factura y devolver la factura guardada
        return billRepository.save(bill);
    }

    /**
     * Eliminar una factura 
     * La factura para eliminar
     * @param bill 
     */
    
    @Override
    public void eliminate(Bills bill) {
        billRepository.delete(bill);
    }

    /**
     * Objeto Bills en un objeto BillResponseDTO
     * El objeto Bills que se va a convertir
     * @param bill 
     * Objeto BillResponseDTO con los datos de la factura y el cliente
     * @return 
     */
    private BillResponseDTO toResponseDTO(Bills bill) {
        // Convertir una factura en su DTO response
        return new BillResponseDTO(
            bill.getIdBill(),
            bill.getTotalBill(),
            bill.getPaymentMethod(),
            // va a agregar el cliente dentro del DTO de la factura
            bill.getClients()
        );
    }
}