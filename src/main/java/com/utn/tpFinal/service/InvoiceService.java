package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Invoice;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.exception.InvoiceExistException;
import com.utn.tpFinal.exception.InvoiceNotExistExpection;
import com.utn.tpFinal.exception.TariffExistException;
import com.utn.tpFinal.repository.InvoiceRepository;
import com.utn.tpFinal.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;

@Service
public class InvoiceService {

    private InvoiceRepository invoiceRepository;
    private static final String INVOICE_PATH ="Invoice";

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRespository) {
        this.invoiceRepository = invoiceRespository;
    }

    public Invoice addInvoice(Invoice newInvoice) throws InvoiceExistException {
        if (!invoiceRepository.existsById(newInvoice.getInvoiceId()))
        {
            return invoiceRepository.save(newInvoice);
        }
        else
        {
            throw new InvoiceExistException();
        }
    }

    public Invoice getInvoiceById(Integer invoiceId) throws InvoiceNotExistExpection
    {
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public void deleteInvoceById(Integer invoiceId) {

        invoiceRepository.deleteById(invoiceId);
    }

    public Page<Invoice> getAllInvoice(Pageable pageable)
    {
        return invoiceRepository.findAll(pageable);
    }

    public Page<Invoice> getInvoiceByRank(Integer idClient, Date start, Date end, Pageable pageable) {

        return invoiceRepository.findByClientBetweenDates(idClient,start,end,pageable);
    }

    public Page<Invoice> findAllResidenceClientUserId(Integer idClient, Integer idResidences, Pageable pageable) {
        return invoiceRepository.findAllResidenceClientUserId(idClient,idResidences,pageable);
    }

    public Page<Invoice> getInvoiceDebt(Integer idClient, Pageable pageable) {
        return invoiceRepository.findByResidentClientIdIsPaidFalse(idClient,pageable);
    }
}
