package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Invoice;
import com.utn.tpFinal.exception.InvoiceExistException;
import com.utn.tpFinal.exception.InvoiceNotExistExpection;
import com.utn.tpFinal.repository.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.utn.tpFinal.Utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class InvoiceServiceTest
{
    @InjectMocks
    InvoiceService invoiceService;

    @Mock
    InvoiceRepository invoiceRepository;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        invoiceService = new InvoiceService(invoiceRepository);
    }

    @Test
    public void getAllInvoiceIsOk()
    {

        when(invoiceRepository.findAll(any(Pageable.class))).thenReturn(anInvoicePage());

        Page<Invoice>invoicePage = invoiceService.getAllInvoice(aPageable());

        assertEquals(anInvoicePage().getTotalElements(), invoicePage.getTotalElements());
        assertEquals(anInvoicePage().getContent().get(0).getLastReading(), invoicePage.getContent().get(0).getLastReading());

        verify(invoiceRepository, times(1)).findAll(aPageable());
    }

    @Test
    public void getByIdOk() throws InvoiceNotExistExpection
    {

            when(invoiceRepository.findById(anyInt())).thenReturn(java.util.Optional.of(anInvoice()));

            Invoice invoice = invoiceService.getInvoiceById(anyInt());

            verify(invoiceRepository, times(1)).findById(anyInt());

    }

    @Test
    public void geByIdException() throws InvoiceNotExistExpection {

        when(invoiceRepository.findById(anyInt())).thenReturn(java.util.Optional.of(anInvoice()));

        invoiceService.getInvoiceById(anyInt());

        verify(invoiceRepository,times(1)).findById(anyInt());
    }

    @Test
    public void addInvoiceOk() throws InvoiceExistException {
        when(invoiceRepository.save(anInvoice())).thenReturn(anInvoice());

        Invoice invoice = invoiceService.addInvoice(anInvoice());

        assertEquals(anInvoice().getFirstReading(),invoice.getFirstReading());
    }
}
