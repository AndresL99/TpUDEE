package com.utn.tpFinal.controller.backoffice;

import com.utn.tpFinal.domain.Invoice;
import com.utn.tpFinal.domain.Measurement;
import com.utn.tpFinal.domain.dto.UserDTO;
import com.utn.tpFinal.domain.projection.Top10MoreConsumption;
import com.utn.tpFinal.service.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.utn.tpFinal.Utils.TestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientBackControllerTest
{
    private ClientService clientService;
    private InvoiceService invoiceService;
    private AddressService addressService;
    private MeterService meterService;
    private MeasurementService measurementService;
    private ClientBackController clientBackController;
    Authentication authentication;
    private UserDTO backOffice;


    @BeforeEach
    public void setUp()
    {
        clientService = mock(ClientService.class);
        invoiceService = mock(InvoiceService.class);
        addressService = mock(AddressService.class);
        meterService = mock(MeterService.class);
        measurementService = mock(MeasurementService.class);
        authentication = mock(Authentication.class);
        backOffice = aBackOffice();
        clientBackController = new ClientBackController(clientService,invoiceService,addressService,meterService,measurementService);
    }

    @SneakyThrows
    @Test
    public void getTopConsumersOk()
    {
        Date start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-06-06 20:12:30");
        Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-06-25 10:05:10");

        Top10MoreConsumption top10MoreConsumption = mock(Top10MoreConsumption.class);

        when(authentication.getPrincipal()).thenReturn(backOffice);
        when(clientService.getTop10MoreConsumtion(start,end)).thenReturn(List.of(top10MoreConsumption));

        ResponseEntity<List<Top10MoreConsumption>>responseEntity = clientBackController.getTopTenConsumtion(authentication,start,end);

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
    }

    @SneakyThrows
    @Test
    public void getTopConsumersNoContent()
    {
        Date start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-06-06 20:12:30");
        Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-06-25 10:05:10");

        Top10MoreConsumption top10MoreConsumption = mock(Top10MoreConsumption.class);

        when(authentication.getPrincipal()).thenReturn(backOffice);
        when(clientService.getTop10MoreConsumtion(start,end)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Top10MoreConsumption>>responseEntity = clientBackController.getTopTenConsumtion(authentication,start,end);

        assertEquals(HttpStatus.NO_CONTENT.value(),responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody().isEmpty());
    }


    @SneakyThrows
    @Test
    public void getMesurementByResidenceAndRankTestOk()
    {

        Date start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-03-06 12:09:15");
        Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-04-10 07:10:00");
        when(authentication.getPrincipal()).thenReturn(backOffice);
        when(measurementService.getMeasurementByResidenceAndRank(22,start,end,aPageable())).thenReturn(aMeasurementPage());

        ResponseEntity<List<Measurement>>responseEntity = clientBackController.getMesurementByResidenceAndRank(authentication,22,start,end,aPageable());

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());

        assertEquals(aMeasurementPage().getContent().get(0).getMeasurementId(), responseEntity.getBody().get(0).getMeasurementId());
        //assertEquals(List.of(aMeasurement()),responseEntity.getBody());
    }

    @Test
    public void getMeaurementByResidenceAndRankTestNotContent() throws ParseException {
        Pageable pageable = PageRequest.of(5,10);
        Page<Measurement> pageM = mock(Page.class);
        Date start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-03-06 12:09:15");
        Date end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-04-10 07:10:00");
        when(authentication.getPrincipal()).thenReturn(backOffice);
        when(measurementService.getMeasurementByResidenceAndRank(aResidence().getResidenceId(),start,end,pageable)).thenReturn(Page.empty());

        ResponseEntity<List<Measurement>>responseEntity = clientBackController.getMesurementByResidenceAndRank(authentication,aResidence().getResidenceId(),start,end,pageable);

        assertEquals(HttpStatus.NO_CONTENT.value(),responseEntity.getStatusCodeValue());
        assertEquals(new ArrayList<>(),responseEntity.getBody());
    }

    @Test
    public void getDebInvoicesTestOk()
    {
        when(authentication.getPrincipal()).thenReturn(backOffice);
        when(invoiceService.getInvoiceDebt(10,aPageable())).thenReturn(anInvoicePage());

        ResponseEntity<List<Invoice>>responseEntity = clientBackController.getDebInvoices(authentication,10,22,aPageable());

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(anInvoicePage().getContent().get(0).getInvoiceId(),responseEntity.getBody().get(0).getInvoiceId());
    }

    @Test
    public void getDebInvoicesTestNotContent()
    {
        Pageable pageable = PageRequest.of(5,10);
        Page<Invoice> invoicePage = mock(Page.class);

        when(authentication.getPrincipal()).thenReturn(backOffice);
        when(invoiceService.getInvoiceDebt(10,pageable)).thenReturn(Page.empty());

        ResponseEntity<List<Invoice>>responseEntity = clientBackController.getDebInvoices(authentication,aClient().getClientId(),aResidence().getResidenceId(),pageable);

        assertEquals(HttpStatus.NO_CONTENT.value(),responseEntity.getStatusCodeValue());
        assertEquals(0,responseEntity.getBody());
    }


}
