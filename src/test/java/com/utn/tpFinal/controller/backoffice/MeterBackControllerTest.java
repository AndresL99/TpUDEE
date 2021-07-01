package com.utn.tpFinal.controller.backoffice;

import com.utn.tpFinal.controller.MeterController;
import com.utn.tpFinal.domain.Meter;
import com.utn.tpFinal.domain.dto.UserDTO;
import com.utn.tpFinal.exception.MeterExistException;
import com.utn.tpFinal.exception.MeterNotExistException;
import com.utn.tpFinal.utils.EntityURLBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;

import static com.utn.tpFinal.Utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MeterBackControllerTest
{

    private MeterController meterController;
    private MeterBackController meterBackController;
    Authentication authentication;
    private UserDTO admin;

    @BeforeEach
    public void setUp()
    {
        meterController = mock(MeterController.class);
        authentication = mock(Authentication.class);
        admin = aBackOffice();
        meterBackController = new MeterBackController(meterController);

    }

    @Test
    public void getByIdOk() throws MeterNotExistException {
        when(authentication.getPrincipal()).thenReturn(admin);
        when(meterController.getMeterById(23)).thenReturn(aMeter());

        ResponseEntity<Meter> response = meterBackController.getMeterById(authentication,23);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(aMeter().getMeterId(),response.getBody().getMeterId());
    }

    @Test
    public void getByIdNotOk()
    {
        when(authentication.getPrincipal()).thenReturn(admin);
        when(meterController.getMeterById(anyInt())).thenThrow(new MeterNotExistException());

        assertThrows(MeterNotExistException.class, ()-> {meterBackController.getMeterById(authentication,anyInt());});
    }

    @Test
    public void addMeterOk() throws MeterExistException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(authentication.getPrincipal()).thenReturn(admin);
        when(meterController.addMeter(aMeter())).thenReturn(aMeter());

        ResponseEntity responseEntity = meterBackController.addMeter(authentication,aMeter());

        assertEquals(HttpStatus.CREATED.value(),responseEntity.getStatusCodeValue());
        assertEquals(EntityURLBuilder.buildURL("Meter",String.valueOf(aMeter().getMeterId())).toString(),responseEntity.getHeaders().get("Location").get(0));
    }

    @Test
    public void getAllMeterOk()
    {
        when(authentication.getPrincipal()).thenReturn(admin);
        when(meterController.getAllMeter(any(Pageable.class))).thenReturn(aMeterPage());

        ResponseEntity<List<Meter>>entity = meterBackController.getAllMeters(authentication,aPageable());

        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(aMeterPage().getTotalElements(),Long.valueOf(entity.getHeaders().get("X-Total-Pages").get(0)));
        assertEquals(aMeterPage().getContent().get(0).getMeterId(),entity.getBody().get(0).getMeterId());
    }

    @Test
    public void getAllEmpty()
    {
        Pageable pageable = PageRequest.of(5,10);
        Page<Meter> page = mock(Page.class);

        when(authentication.getPrincipal()).thenReturn(admin);
        when(page.getContent()).thenReturn(Collections.emptyList());
        when(meterController.getAllMeter(pageable)).thenReturn(page);

        ResponseEntity<List<Meter>>responseEntity = meterBackController.getAllMeters(authentication,pageable);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(0,responseEntity.getBody().size());
    }
}
