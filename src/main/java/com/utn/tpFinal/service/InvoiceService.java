package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Invoice;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.domain.dto.ConsumeptionAndCostDTO;
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

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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

    public Page<Invoice> getInvoiceByRank(Integer idClient, LocalDateTime start, LocalDateTime end, Pageable pageable) {

        return invoiceRepository.findByClientBetweenDates(idClient,start,end,pageable);
    }

    //4 Back
    public Page<Invoice> findAllResidenceClientUserId(Integer idResidences,Integer idClient, Pageable pageable) {
        return invoiceRepository.findAllByResidence_ResidenceIdAndAndResidence_ClientClientIdAndAndIsPaidFalse(idResidences,idClient,pageable);
    }

    public Page<Invoice> getInvoiceDebt(Integer idClient, Pageable pageable) {
        return invoiceRepository.findByResidence_Client_ClientIdAndAndIsPaidFalse(idClient,pageable);
    }

    public Page<ConsumeptionAndCostDTO> getTotalConsumeAndCost(Integer idClient, LocalDateTime start, LocalDateTime end, Pageable pageable) {

        List<Invoice> invoices = (List<Invoice>) invoiceRepository.findByClientBetweenDates(idClient,start,end,pageable);
        ConsumeptionAndCostDTO consume = new ConsumeptionAndCostDTO();

        if(!invoices.isEmpty()){

            for ( Invoice i : invoices) {

                consume.setTotalKwh(consume.getTotalKwh() + i.getTotalConsumptionKwh() );
                consume.setTotalCost(consume.getTotalCost() + i.getTotalAmount() );

            }
        }

        return (Page<ConsumeptionAndCostDTO>) consume;
    }
}
