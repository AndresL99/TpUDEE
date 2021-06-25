package com.utn.tpFinal.backoffice;


import com.utn.tpFinal.controller.AddressController;
import com.utn.tpFinal.controller.ClientController;
import com.utn.tpFinal.controller.InvoiceController;
import com.utn.tpFinal.controller.MeterController;
import com.utn.tpFinal.domain.Client;
import com.utn.tpFinal.domain.Invoice;
import com.utn.tpFinal.domain.Measurement;
import com.utn.tpFinal.domain.Residence;
import com.utn.tpFinal.domain.proyection.Top10MoreConsumption;
import com.utn.tpFinal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RestController
@RequestMapping("/Back/Client")
public class ClientBackController {

    /**
     * 4) Consulta de facturas impagas por cliente y domicilio.
     * 5) Consulta 10 clientes más consumidores en un rango de fechas.
     * 6) Consulta de mediciones de un domicilio por rango de fechas
     */

    ClientService clientService;
    InvoiceService invoiceService;
    AddressService addressService;
    MeterService meterService;
    MeasurementService measurementService;

    @Autowired
    public ClientBackController(ClientService clientService, InvoiceService invoiceService, AddressService addressService, MeterService meterService, MeasurementService measurementService) {
        this.clientService = clientService;
        this.invoiceService = invoiceService;
        this.addressService = addressService;
        this.meterService = meterService;
        this.measurementService = measurementService;
    }

    //4) Consulta de facturas impagas por cliente y domicilio.
    @GetMapping("{idClient}/residences/{idResidence}/invoices/deb")
    public ResponseEntity<List<Invoice>> getDebInvoices(@PathVariable Integer idClient, @PathVariable Integer idResidences, Pageable pageable){
            Page<Invoice>invoicesDeb=invoiceService.findAllResidenceClientUserId(idClient,idResidences,pageable);
        return response(invoicesDeb);
    }

    //5) Consulta 10 clientes más consumidores en un rango de fechas.
    @GetMapping("/client/top10")
    public ResponseEntity<List<Top10MoreConsumption>>getTopTenConsumtion(@RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime from,
                                                           @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime to){
        List<Top10MoreConsumption>top=clientService.getTop10MoreConsumtion(from,to);
        return response(top);
    }


    //6) Consulta de mediciones de un domicilio por rango de fechas
    @GetMapping("/residence/{idResidence}")
    public ResponseEntity<List<Measurement>> getMesurementByResidenceAndRank(@PathVariable Integer idResidence,
                                                                             @RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime from,
                                                                             @RequestParam("to") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime to,
                                                                             Pageable pageable){
    Page<Measurement>mesurements= measurementService.getMeasurementByResidenceAndRank(idResidence,from,to,pageable);
    return response(mesurements);

    }





    private ResponseEntity response(List list, Page page) {
        HttpStatus status = !list.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return ResponseEntity.status(status).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());
    }


    private ResponseEntity response(List list) {
        return ResponseEntity.status(list.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(list);
    }

    private ResponseEntity response(Page page) {
        HttpStatus httpStatus = page.getContent().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.
                status(httpStatus).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());

    }

}
