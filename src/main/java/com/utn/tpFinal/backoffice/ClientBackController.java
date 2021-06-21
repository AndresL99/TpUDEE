package com.utn.tpFinal.backoffice;


import com.utn.tpFinal.controller.AddressController;
import com.utn.tpFinal.controller.ClientController;
import com.utn.tpFinal.controller.InvoiceController;
import com.utn.tpFinal.controller.MeterController;
import com.utn.tpFinal.domain.Invoice;
import com.utn.tpFinal.domain.Residence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/Back/Client")
public class ClientBackController {

    /**
     * 4) Consulta de facturas impagas por cliente y domicilio.
     * 5) Consulta 10 clientes más consumidores en un rango de fechas.
     * 6) Consulta de mediciones de un domicilio por rango de fechas
     */

    ClientController clientController;
    InvoiceController invoiceController;
    AddressController addressController;
    MeterController meterController;


    public ClientBackController(ClientController clientController, InvoiceController invoiceController, AddressController addressController, MeterController meterController) {
        this.clientController = clientController;
        this.invoiceController = invoiceController;
        this.addressController = addressController;
        this.meterController = meterController;
    }

    //




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
