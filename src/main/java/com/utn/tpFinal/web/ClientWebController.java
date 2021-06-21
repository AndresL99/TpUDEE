package com.utn.tpFinal.web;

import com.utn.tpFinal.controller.ClientController;
import com.utn.tpFinal.controller.InvoiceController;
import com.utn.tpFinal.controller.MeterController;
import com.utn.tpFinal.controller.ResidenceController;
import com.utn.tpFinal.domain.Residence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    ClientController clientController;
    MeterController meterController;
    InvoiceController invoiceController;
    ResidenceController residenceController;

    @Autowired
    public ClientWebController(ClientController clientController, MeterController meterController, InvoiceController invoiceController, ResidenceController residenceController) {
        this.clientController = clientController;
        this.meterController = meterController;
        this.invoiceController = invoiceController;
        this.residenceController = residenceController;
    }



    //Traer todos las residences por cliente

    @GetMapping("{idClient}/Residences")
    public ResponseEntity<List<Residence>> getResidencesByUser(@PathVariable Integer idClient, Pageable pageable){
        Page page = residenceController.getResidenceByUser(idClient,pageable);
        return response(page);

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





    //2


}
