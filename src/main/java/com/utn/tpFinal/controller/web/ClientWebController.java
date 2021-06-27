package com.utn.tpFinal.controller.web;

import com.utn.tpFinal.controller.*;
import com.utn.tpFinal.domain.*;
import com.utn.tpFinal.domain.dto.ConsumeptionAndCostDTO;
import com.utn.tpFinal.domain.dto.UserDTO;
import com.utn.tpFinal.exception.ClientNotExistException;
import com.utn.tpFinal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
@RestController
@RequestMapping("/Web/Client")
public class ClientWebController {

    private ClientService clientService;
    private ResidenceService residenceService;
    private InvoiceService invoiceService;
    private MeasurementService measurementService;
    private UserService userService;

    /**
     *1) Login de clientes
     * 2) Consulta de facturas por rango de fechas.
     * 3) Consulta de deuda (Facturas impagas)
     * 4) Consulta de consumo por rango de fechas (el usuario va a ingresar un rango
     * de fechas y quiere saber cuánto consumió en ese periodo en Kwh y dinero)
     * 5) Consulta de mediciones por rango de fechas
     */

    public ClientWebController(ClientService clientService, ResidenceService residenceService, InvoiceService invoiceService, MeasurementService measurementService,UserService userService) {
        this.clientService = clientService;
        this.residenceService = residenceService;
        this.invoiceService = invoiceService;
        this.measurementService = measurementService;
        this.userService = userService;
    }

    //Traer todos las residences por cliente

    /*@GetMapping("{idClient}/Residences")
    public ResponseEntity<List<Residence>> getResidencesByUser(@PathVariable Integer idClient, Pageable pageable){
        Page page = residenceController.getResidenceByUser(idClient,pageable);
        return response(page);

    }*/

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

    @GetMapping("{id}/consumption")
    public ResponseEntity<ConsumeptionAndCostDTO> getConsumptionAndCost(@PathVariable Integer idClient,
                                                                        @RequestParam @DateTimeFormat(pattern="MM-yyyy") Date start,
                                                                        @RequestParam @DateTimeFormat(pattern="MM-yyyy") Date end,
                                                                        Pageable pageable){
        Page<ConsumeptionAndCostDTO>consumeAndCost= invoiceService.getTotalConsumeAndCost(idClient,start,end,pageable);

        return response(consumeAndCost);
    }

    //5) Consulta de mediciones por rango de fechas
    @GetMapping("{id}/measurement")
    public ResponseEntity<List<Measurement>>getMeasurementByRank(@PathVariable Integer idClient,
                                                                 @RequestParam @DateTimeFormat(pattern="MM-yyyy") Date start,
                                                                 @RequestParam @DateTimeFormat(pattern="MM-yyyy") Date end,
                                                                 Pageable pageable){
        Page<Measurement>measurements = measurementService.getMeasurementByRank(idClient,start,end,pageable);

        return response(measurements);
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


        private void verifyAuthClient(Authentication authentication,Integer idClient)
        {
            Client client = this.clientService.getByUserName(((UserDTO)authentication.getPrincipal()).getUsername());
            if(!client.getClientId().equals(idClient))
            {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Denied Access");
            }
        }


}
