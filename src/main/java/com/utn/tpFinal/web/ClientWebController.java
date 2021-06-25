package com.utn.tpFinal.web;

import com.utn.tpFinal.controller.ClientController;
import com.utn.tpFinal.controller.InvoiceController;
import com.utn.tpFinal.controller.MeterController;
import com.utn.tpFinal.controller.ResidenceController;
import com.utn.tpFinal.domain.Invoice;
import com.utn.tpFinal.domain.Residence;
import com.utn.tpFinal.exception.ClientNotExistException;
import com.utn.tpFinal.service.ClientService;
import com.utn.tpFinal.service.InvoiceService;
import com.utn.tpFinal.service.MeasurementService;
import com.utn.tpFinal.service.ResidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RestController
@RequestMapping("/Web/Client")
public class ClientWebController {

    /**
     *1) Login de clientes
     * 2) Consulta de facturas por rango de fechas.
     * 3) Consulta de deuda (Facturas impagas)
     * 4) Consulta de consumo por rango de fechas (el usuario va a ingresar un rango
     * de fechas y quiere saber cuánto consumió en ese periodo en Kwh y dinero)
     * 5) Consulta de mediciones por rango de fechas
     */

  ClientService clientService;
  ResidenceService residenceService;
  InvoiceService invoiceService;
  MeasurementService measurementService;

    @Autowired
    public ClientWebController(ClientService clientService, ResidenceService residenceService, InvoiceService invoiceService, MeasurementService measurementService) {
        this.clientService = clientService;
        this.residenceService = residenceService;
        this.invoiceService = invoiceService;
        this.measurementService = measurementService;
    }

    //Traer todos las residences por cliente

    @GetMapping("{idClient}/Residences")
    public ResponseEntity<List<Residence>> getResidencesByUser(@PathVariable Integer idClient, Pageable pageable){
        Page page = residenceService.getResidenceByClientId(idClient,pageable);
        return response(page);

    }

    //2) Consulta de facturas por rango de fechas.
    @GetMapping("/{id}/invoices")
    public ResponseEntity<List<Invoice>>getInvoiceRankDate(@PathVariable Integer idClient,
                                                           @RequestParam @DateTimeFormat(pattern="MM-yyyy") Date start,
                                                           @RequestParam @DateTimeFormat(pattern="MM-yyyy") Date end,
                                                           Pageable pageable ) throws ClientNotExistException {
        Page<Invoice>invoices =invoiceService.getInvoiceByRank(idClient,start,end,pageable);
        return response(invoices);

    }
    // 3) Consulta de deuda (Facturas impagas)
    @GetMapping("/{id}/invoice/debt")
    public ResponseEntity<List<Invoice>> getInvoiceDebt (@PathVariable Integer idClient,Pageable pageable){

        Page<Invoice>invoices = invoiceService.getInvoiceDebt(idClient,pageable);
        return response(invoices);
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
