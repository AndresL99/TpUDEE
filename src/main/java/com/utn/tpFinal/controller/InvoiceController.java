package com.utn.tpFinal.controller;

import com.utn.tpFinal.domain.Invoice;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/Invoice")
public class InvoiceController {

    private InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public PostResponse addInvoice(@RequestBody Invoice newInvoice){
        return invoiceService.addInvoice(newInvoice);
    }

    @GetMapping("/{invoiceId}")
    public Invoice getInvoiceById(@PathVariable Integer invoiceId){
        return invoiceService.getInvoiceById(invoiceId);
    }

    @DeleteMapping("/{invoiceId}")
    public void deleteInvoiceById(@PathVariable Integer invoiceId){
        invoiceService.deleteInvoceById(invoiceId);
    }
}
