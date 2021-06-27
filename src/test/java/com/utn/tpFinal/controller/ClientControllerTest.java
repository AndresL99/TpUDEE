package com.utn.tpFinal.controller;

import com.utn.tpFinal.AbstractControllerTest;
import com.utn.tpFinal.domain.Client;
import com.utn.tpFinal.domain.Invoice;
import com.utn.tpFinal.exception.ClientExistException;
import com.utn.tpFinal.exception.ClientNotExistException;
import com.utn.tpFinal.exception.InvoiceNotExistExpection;
import com.utn.tpFinal.service.ClientService;
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

import java.util.Collections;
import java.util.List;

import static com.utn.tpFinal.Utils.TestUtils.*;
import static com.utn.tpFinal.Utils.TestUtils.anInvoicePage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ClientControllerTest extends AbstractControllerTest
{

    private ClientService clientService;

     private ClientController clientController;

    @BeforeEach
    public void setUp()
    {
        clientService = mock(ClientService.class);
        clientController = new ClientController(clientService);
    }

    @Test
    public void addClientOk() throws ClientExistException {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

        when(clientService.addClient(aClient())).thenReturn(aClient());

        ResponseEntity responseEntity = clientController.addClient(aClient());

        assertEquals(HttpStatus. CREATED.value(),responseEntity.getStatusCodeValue());
        assertEquals(EntityURLBuilder.buildURL("Client",String.valueOf(aClient().getClientId())).toString(), responseEntity.getHeaders().get("Location").get(0));
    }

    @Test
    public void getAllOk()
    {
        when(clientService.getAllClient(any(Pageable.class))).thenReturn(aClientPage());

        ResponseEntity<List<Client>>listResponseEntity = clientController.getAllClient(aPageable());

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
        assertEquals(aClientPage().getTotalElements(),Long.valueOf(listResponseEntity.getHeaders().get("X-Total-Pages").get(0)));
        assertEquals(aClientPage().getContent().get(0).getClientId(),listResponseEntity.getBody().get(0).getClientId());
    }

    @Test
    public void getAllNotOkay()
    {
        Pageable pageable = PageRequest.of(5,10);
        Page<Client> clientPage = mock(Page.class);

        when(clientPage.getContent()).thenReturn(Collections.emptyList());
        when(clientService.getAllClient(pageable)).thenReturn(clientPage);

        ResponseEntity<List<Client>>responseEntity = clientController.getAllClient(pageable);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(0,responseEntity.getBody().size());
    }

    @Test
    public void getByIdOk() throws ClientNotExistException {
        Integer id_client = aClient().getClientId();
        when(clientService.getClientById(id_client)).thenReturn(aClient());

        ResponseEntity<Client>responseEntity = clientController.getClientById(id_client);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(aClient().getClientId(),responseEntity.getBody().getClientId());
    }

    @Test
    public void getByIdException() throws ClientNotExistException {
        when(clientService.getClientById(anyInt())).thenThrow(new ClientNotExistException());

        assertThrows(ClientNotExistException.class, ()-> {clientController.getClientById(anyInt());});
    }

    @Test
    public void deleteOk()
    {
        
    }

}
