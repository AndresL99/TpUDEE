package com.utn.tpFinal.controller;

import com.utn.tpFinal.domain.Invoice;
import com.utn.tpFinal.domain.Meter;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.exception.InvoiceExistException;
import com.utn.tpFinal.exception.InvoiceNotExistExpection;
import com.utn.tpFinal.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RestController
@RequestMapping("/Invoice")
public class InvoiceController {

    private InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity addInvoice(@RequestBody Invoice invoice) throws InvoiceExistException {
        Invoice i = invoiceService.addInvoice(invoice);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{invoiceId}")
                .buildAndExpand("Invoice/"+i.getInvoiceId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "{invoiceId}", produces = "application/json")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable("invoiceId") Integer invoiceId) throws InvoiceNotExistExpection
    {
        Invoice invoice = invoiceService.getInvoiceById(invoiceId);
        return ResponseEntity.ok(invoice);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Invoice>> getAllInvoice(Pageable pageable) {
        Page page = invoiceService.getAllInvoice(pageable);
        return response(page);
    }

    @DeleteMapping("/{invoiceId}")
    public void deleteInvoiceById(@PathVariable Integer invoiceId){
        invoiceService.deleteInvoceById(invoiceId);
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
