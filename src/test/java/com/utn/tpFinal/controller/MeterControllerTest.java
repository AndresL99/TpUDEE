package com.utn.tpFinal.controller;

import com.utn.tpFinal.AbstractControllerTest;
import com.utn.tpFinal.domain.Meter;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.exception.MeterNotExistException;
import com.utn.tpFinal.exception.TariffExistException;
import com.utn.tpFinal.exception.TariffNotExistException;
import com.utn.tpFinal.service.MeterService;
import com.utn.tpFinal.service.TariffService;
import com.utn.tpFinal.utils.EntityURLBuilder;
import org.hibernate.engine.spi.ManagedEntity;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;

import static com.utn.tpFinal.Utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MeterControllerTest extends AbstractControllerTest
{
    private MeterService meterService;
    private MeterController meterController;

    @BeforeEach
    public void setUp()
    {
        meterService = mock(MeterService.class);
        meterController = new MeterController(meterService);
    }
    @Test
    public void getAllMeterTestOk() {
        when(meterService.getAllMeter(any(Pageable.class))).thenReturn(aMeterPage());

        Page<Meter> response = meterController.getAllMeter(aPageable());

        assertEquals(aMeterPage().getContent().get(0).getMeterId(), response.getContent().get(0).getMeterId());
    }

    @Test
    public void getMeterByIdTestIsOk() {
        when(meterService.getMeterById(anyInt())).thenReturn(aMeter());

        Meter meter = meterController.getMeterById(anyInt());

        assertEquals(aMeter().getMeterId(), meter.getMeterId());
    }

    @Test
    public void getMeterByIdWithExceptionTest()
    {
        when(meterService.getMeterById(anyInt())).thenThrow(new MeterNotExistException());
        assertThrows(MeterNotExistException.class, () -> {
            meterController.getMeterById(anyInt());
        });
    }


    @Test
    public void getAllNotContent()
    {
        Pageable pageable = PageRequest.of(5,10);
        Page<Meter>pageM = mock(Page.class);

        when(pageM.getContent()).thenReturn(Collections.emptyList());
        when(meterService.getAllMeter(pageable)).thenReturn(pageM);

        Page<Meter>page = meterController.getAllMeter(pageable);

        assertEquals(0,page.getContent().size());
    }

    @Test
    public void updateOK()
    {
        meterService.update(aMeter().getMeterId(), aMeter());

        verify(meterService, times(1)).update(aMeter().getMeterId(),aMeter());
    }

    @Test
    public void addMeterOk() throws TariffExistException {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

        when(meterService.addMeter(aMeter())).thenReturn(aMeter());

        Meter meter = meterController.addMeter(aMeter());

        assertEquals(aMeter().getMeterId(),aMeter().getMeterId());
    }
}
