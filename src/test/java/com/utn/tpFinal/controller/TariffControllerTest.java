package com.utn.tpFinal.controller;

import com.utn.tpFinal.AbstractControllerTest;
import com.utn.tpFinal.domain.Address;
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
import org.springframework.core.convert.ConversionService;
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
import java.util.Collections;
import java.util.List;

import static com.utn.tpFinal.Utils.TestUtils.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TariffControllerTest {

    private TariffService tariffService;
    private TariffController tariffController;
    private ConversionService conversionService;

    @BeforeEach
    public void setUp() {
        tariffService = mock(TariffService.class);
        conversionService = mock(ConversionService.class);
        tariffController = new TariffController(tariffService, conversionService);
    }

    @Test
    public void getAllTariffTestOk() {
        when(tariffService.getAllTariff(any(Pageable.class))).thenReturn(aTariffPage());

        Page<Tariff> response = tariffController.getAll(aPageable());

        assertEquals(aTariffPage().getContent().get(0).getTariffName(), response.getContent().get(0).getTariffName());
    }

    @Test
    public void getTariffByIdTestIsOk() {
        try {
            when(tariffService.getTariffById(anyInt())).thenReturn(aTariff());

            Tariff tariff = tariffController.getTariffById(anyInt());

            assertEquals(aTariff().getTariffName(), tariff.getTariffName());
        } catch (TariffNotExistException e) {
            fail();
        }
    }

    @Test
    public void getTariffByIdWithExceptionTest() throws TariffNotExistException {
        when(tariffService.getTariffById(anyInt())).thenThrow(new TariffNotExistException());
        assertThrows(TariffNotExistException.class, () -> {
            tariffController.getTariffById(anyInt());
        });
    }


    @Test
    public void getAllNotContent()
    {
        Pageable pageable = PageRequest.of(5,10);
        Page<Tariff>pageM = mock(Page.class);

        when(pageM.getContent()).thenReturn(Collections.emptyList());
        when(tariffService.getAllTariff(pageable)).thenReturn(pageM);

        Page<Tariff>page = tariffController.getAll(pageable);

        assertEquals(0,page.getContent().size());
    }

    @Test
    public void updateOK() {

        tariffController.update(aTariff().getTariffId(), aTariff());

        verify(tariffService, times(1)).update(aTariff().getTariffId(), aTariff());
    }

    @Test
    public void addTariffOk() throws TariffExistException {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

        when(tariffService.addTariff(aTariff())).thenReturn(aTariff());

        Tariff tariff = tariffController.addTariff(aTariff());

        assertEquals(aTariff().getTariffId(),tariff.getTariffId());
    }
}