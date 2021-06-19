package com.utn.tpFinal.controller;

import com.utn.tpFinal.AbstractControllerTest;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.exception.TariffExistException;
import com.utn.tpFinal.exception.TariffNotExistException;
import com.utn.tpFinal.service.TariffService;
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


import java.util.ArrayList;
import java.util.List;

import static com.utn.tpFinal.Utils.TestUtils.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TariffController.class)
public class TariffControllerTest extends AbstractControllerTest
{
    @Mock
    TariffService tariffService;
    TariffController tariffController;

    @BeforeEach
    public void setUp()
    {
        tariffService = mock(TariffService.class);
        tariffController = new TariffController(tariffService);
    }

    @Test
    public void getAllTariffTestOk()
    {
        when(tariffService.getAllTariff(any(Pageable.class))).thenReturn(aTariffPage());

        ResponseEntity<List<Tariff>> response = tariffController.getAllTariff(aPageable());


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(aTariffPage().getTotalElements(), Long.valueOf(response.getHeaders().get("X-Total-Pages").get(0)));
        assertEquals(aTariffPage().getContent().get(0).getTariffName(), response.getBody().get(0).getTariffName());
    }

    @Test
    public void getTariffByIdTestIsOk()
    {
        try
        {
            when(tariffService.getTariffById(anyInt())).thenReturn(aTariff());

            ResponseEntity<Tariff>responseEntity = tariffController.getTariffById(1);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(aTariff().getTariffName(), responseEntity.getBody().getTariffName());
        }
        catch(TariffNotExistException e)
        {
            fail();
        }
    }

    @Test
    public void getTariffByIdWithExceptionTest() throws TariffNotExistException
    {
            when(tariffService.getTariffById(anyInt())).thenThrow(new TariffNotExistException());
            assertThrows(TariffNotExistException.class, () -> {tariffController.getTariffById(4);});
    }

    @Test
    public void addTariffOk()
    {
        try
        {
            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            when(tariffService.addTariff(aTariff())).thenReturn(aTariff());

            ResponseEntity response = tariffController.newTariff(aTariff());

            assertEquals(
                    HttpStatus.CREATED.value(),
                    response.getStatusCodeValue());

            assertEquals(
                    EntityURLBuilder.buildURL("tariffs", String.valueOf(aTariff().getTariffId())).toString(),
                    response.getHeaders().get("Location").get(0));
        }
        catch (TariffExistException ex)
        {
            ex.getMessage();
        }
    }

}
