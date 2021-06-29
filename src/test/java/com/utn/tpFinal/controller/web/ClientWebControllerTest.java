package com.utn.tpFinal.controller.web;

import com.utn.tpFinal.domain.Client;
import com.utn.tpFinal.domain.Invoice;
import com.utn.tpFinal.domain.Measurement;
import com.utn.tpFinal.domain.Residence;
import com.utn.tpFinal.domain.dto.ConsumeptionAndCostDTO;
import com.utn.tpFinal.domain.dto.UserDTO;
import com.utn.tpFinal.exception.ClientNotExistException;
import com.utn.tpFinal.service.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ListResourceBundle;

import static com.utn.tpFinal.Utils.TestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientWebControllerTest
{
    private ClientService clientService;
    private ResidenceService residenceService;
    private InvoiceService invoiceService;
    private MeasurementService measurementService;
    private UserService userService;
    private ClientWebController clientWebController;
    Authentication authentication;
    private UserDTO client;

    @BeforeEach
    public void setUp()
    {
        clientService = mock(ClientService.class);
        residenceService = mock(ResidenceService.class);
        invoiceService = mock(InvoiceService.class);
        measurementService = mock(MeasurementService.class);
        userService = mock(UserService.class);
        authentication = mock(Authentication.class);
        client = aClientUser();
        clientWebController = new ClientWebController(clientService,residenceService,invoiceService,measurementService,userService);
    }

    @Test
    public void getResidencesByUserTestOk() throws ClientNotExistException {

        when(authentication.getPrincipal()).thenReturn(UserDTO.builder().username("User1").build());
        when(clientService.getByUserName("User1")).thenReturn(Client.builder().clientId(1).build());
        when(residenceService.getResidenceByClientId(1,aPageable())).thenReturn(aResidencePage());

        ResponseEntity<List<Residence>>responseEntity = clientWebController.getResidencesByUser(authentication,1,aPageable());

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(aResidencePage().getContent().get(0).getResidenceId(),responseEntity.getBody().get(0).getResidenceId());
    }

    @SneakyThrows
    @Test
    public void getMeasurementByRankTestOk()
    {
        Date start = new SimpleDateFormat("MM-yyyy").parse("05-2021");
        Date end = new SimpleDateFormat("MM-yyyy").parse("06-2021");

        when(authentication.getPrincipal()).thenReturn(UserDTO.builder().username("User1").build());
        when(clientService.getByUserName("User1")).thenReturn(Client.builder().clientId(1).build());
        when(measurementService.getMeasurementByRank(1,start,end,aPageable())).thenReturn(aMeasurementPage());

        ResponseEntity<List<Measurement>> responseEntity = clientWebController.getMeasurementByRank(authentication,1,start,end,aPageable());

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(aMeasurementPage().getContent().get(0).getMeasurementId(),responseEntity.getBody().get(0).getMeasurementId());
    }

    @SneakyThrows
    @Test
    public void getInvoiceRankDateOk()
    {
        Date start = new SimpleDateFormat("MM-yyyy").parse("03-2021");
        Date end = new SimpleDateFormat("MM-yyyy").parse("04-2021");
        when(authentication.getPrincipal()).thenReturn(UserDTO.builder().username("User1").build());
        when(clientService.getByUserName("User1")).thenReturn(Client.builder().clientId(10).build());
        when(invoiceService.getInvoiceByRank(10,start,end,aPageable())).thenReturn(anInvoicePage());

        ResponseEntity<List<Invoice>>responseEntity = clientWebController.getInvoiceRankDate(authentication,10,start,end,aPageable());

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(anInvoicePage().getContent().get(0).getInvoiceId(),responseEntity.getBody().get(0).getInvoiceId());
    }

    @SneakyThrows
    @Test
    public void getInvoiceDebtOk()
    {
        Date start = new SimpleDateFormat("MM-yyyy").parse("02-2021");
        Date end = new SimpleDateFormat("MM-yyyy").parse("03-2021");
        when(authentication.getPrincipal()).thenReturn(UserDTO.builder().username("User1").build());
        when(clientService.getByUserName("User1")).thenReturn(Client.builder().clientId(10).build());
        when(invoiceService.getInvoiceDebt(10,aPageable())).thenReturn(anInvoicePage());

        ResponseEntity<List<Invoice>>responseEntity = clientWebController.getInvoiceDebt(authentication,10,aPageable());

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(anInvoicePage().getContent().get(0).getInvoiceId(),responseEntity.getBody().get(0).getInvoiceId());
    }

    @SneakyThrows
    @Test
    public void getConsumptionAndCostOk() {
        Date start = new SimpleDateFormat("MM-yyyy").parse("02-2021");
        Date end = new SimpleDateFormat("MM-yyyy").parse("03-2021");
        when(authentication.getPrincipal()).thenReturn(UserDTO.builder().username("User1").build());
        when(clientService.getByUserName("User1")).thenReturn(Client.builder().clientId(10).build());
        when(invoiceService.getTotalConsumeAndCost(10, start, end, aPageable())).thenReturn(aConsumeptionAndCostDTOPage());

        ResponseEntity<ConsumeptionAndCostDTO> consumeptionAndCostDTOResponseEntity = clientWebController.getConsumptionAndCost(authentication, 10, start, end, aPageable());

        assertEquals(HttpStatus.OK.value(), consumeptionAndCostDTOResponseEntity.getStatusCodeValue());

    }
}
