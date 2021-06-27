package com.utn.tpFinal.controller;

import com.utn.tpFinal.AbstractControllerTest;
import com.utn.tpFinal.domain.Meter;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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
    public void getByIdOk()
    {
        when(meterService.getMeterById(anyInt())).thenReturn(aMeter());

        ResponseEntity<Meter> response = meterController.getMeterById(anyInt());

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(aMeter().getMeterId(),response.getBody().getMeterId());
    }

    @Test
    public void getByIdNotOk()
    {
        when(meterService.getMeterById(anyInt())).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(HttpClientErrorException.class, ()-> {meterController.getMeterById(anyInt());});
    }

    @Test
    public void addMeterOk()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(meterService.addMeter(aMeter())).thenReturn(aMeter());

        ResponseEntity responseEntity = meterController.addMeter(aMeter());

        assertEquals(HttpStatus.CREATED.value(),responseEntity.getStatusCodeValue());
        assertEquals(EntityURLBuilder.buildURL("Meter",String.valueOf(aMeter().getMeterId())).toString(),responseEntity.getHeaders().get("Location").get(0));
    }

    @Test
    public void getAllMeterOk()
    {
        when(meterService.getAllMeter(any(Pageable.class))).thenReturn(aMeterPage());

        ResponseEntity<List<Meter>>entity = meterController.getAllMeter(aPageable());

        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(aMeterPage().getTotalElements(),Long.valueOf(entity.getHeaders().get("X-Total-Pages").get(0)));
        assertEquals(aMeterPage().getContent().get(0).getMeterId(),entity.getBody().get(0).getMeterId());
    }

    @Test
    public void getAllEmpty()
    {
        Pageable pageable = PageRequest.of(5,10);
        Page<Meter>page = mock(Page.class);

        when(page.getContent()).thenReturn(Collections.emptyList());
        when(meterService.getAllMeter(pageable)).thenReturn(page);

        ResponseEntity<List<Meter>>responseEntity = meterController.getAllMeter(pageable);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(0,responseEntity.getBody().size());
    }
}
