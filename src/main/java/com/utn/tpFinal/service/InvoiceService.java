package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Invoice;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.repository.InvoiceRepository;
import com.utn.tpFinal.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class InvoiceService {

    private InvoiceRepository invoiceRepository;
    private static final String INVOICE_PATH ="Invoice";

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRespository) {
        this.invoiceRepository = invoiceRespository;
    }

    public PostResponse addInvoice(Invoice newInvoice) {

        Invoice i = invoiceRepository.save(newInvoice);
        return PostResponse
                .builder()
                .status(HttpStatus.CREATED)
                .url(EntityURLBuilder.buildURL(INVOICE_PATH,i.getInvoiceId().toString()))
                .build();
    }

    public Invoice getInvoiceById(Integer invoiceId) {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public void deleteInvoceById(Integer invoiceId) {

        invoiceRepository.deleteById(invoiceId);
    }
}
