package com.utn.tpFinal.controller;

import com.utn.tpFinal.AbstractControllerTest;
import com.utn.tpFinal.domain.Invoice;
import com.utn.tpFinal.exception.InvoiceExistException;
import com.utn.tpFinal.exception.InvoiceNotExistExpection;
import com.utn.tpFinal.service.InvoiceService;
import com.utn.tpFinal.service.MeterService;
import com.utn.tpFinal.utils.EntityURLBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Year;
import java.util.Collections;
import java.util.List;

import static com.utn.tpFinal.Utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class InvoiceControllerTest extends AbstractControllerTest
{
    private InvoiceService invoiceService;

    private InvoiceController invoiceController;

    @BeforeEach
    public void setUp()
    {
        invoiceService = mock(InvoiceService.class);
        invoiceController = new InvoiceController(invoiceService);
    }

    @Test
    public void addInvoiceOk() throws InvoiceExistException {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

        when(invoiceService.addInvoice(anInvoice())).thenReturn(anInvoice());

        ResponseEntity responseEntity = invoiceController.addInvoice(anInvoice());

        assertEquals(HttpStatus.CREATED.value(),responseEntity.getStatusCodeValue());
        assertEquals(EntityURLBuilder.buildURL("Invoice",String.valueOf(anInvoice().getInvoiceId())).toString(), responseEntity.getHeaders().get("Location").get(0));
    }

    @Test
    public void getAllInvoiceOk()
    {
        when(invoiceService.getAllInvoice(any(Pageable.class))).thenReturn(anInvoicePage());

        ResponseEntity<List<Invoice>>listResponseEntity = invoiceController.getAllInvoice(aPageable());

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
        assertEquals(anInvoicePage().getTotalElements(),Long.valueOf(listResponseEntity.getHeaders().get("X-Total-Pages").get(0)));
        assertEquals(anInvoicePage().getContent().get(0).getFirstReading(),listResponseEntity.getBody().get(0).getFirstReading());
    }

    @Test
    public void getAllNotContentInvoice()
    {
        Pageable pageable = PageRequest.of(5,10);
        Page<Invoice>invoicePage = mock(Page.class);

        when(invoicePage.getContent()).thenReturn(Collections.emptyList());
        when(invoiceService.getAllInvoice(pageable)).thenReturn(invoicePage);

        ResponseEntity<List<Invoice>>responseEntity = invoiceController.getAllInvoice(pageable);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(0,responseEntity.getBody().size());
    }

    @Test
    public void getByIdOk() throws InvoiceNotExistExpection {
        when(invoiceService.getInvoiceById(anyInt())).thenReturn(anInvoice());

        ResponseEntity<Invoice>responseEntity = invoiceController.getInvoiceById(anyInt());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(anInvoice().getFirstReading(),responseEntity.getBody().getFirstReading());
    }

    @Test
    public void getByIdException() throws InvoiceNotExistExpection {
        when(invoiceService.getInvoiceById(anyInt())).thenThrow(new InvoiceNotExistExpection());

        assertThrows(InvoiceNotExistExpection.class, ()-> {invoiceController.getInvoiceById(anyInt());});
    }
}
