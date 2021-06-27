package com.utn.tpFinal.controller;

import com.utn.tpFinal.AbstractControllerTest;
import com.utn.tpFinal.domain.Address;
import com.utn.tpFinal.exception.AddressExistException;
import com.utn.tpFinal.exception.AddressNotExistException;
import com.utn.tpFinal.service.AddressService;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
public class AdressControllerTest extends AbstractControllerTest
{
    private AddressService addressService;

    private AddressController addressController;

    @BeforeEach
    public void setUp()
    {
        addressService = mock(AddressService.class);
        addressController = new AddressController(addressService);
    }

    @Test
    public void addAddressOk() throws AddressExistException {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

        when(addressService.addAddress(aAddress())).thenReturn(aAddress());

        ResponseEntity responseEntity = addressController.addAddress(aAddress());

        assertEquals(HttpStatus.CREATED.value(),responseEntity.getStatusCodeValue());
        assertEquals(EntityURLBuilder.buildURL("Address",String.valueOf(aAddress().getAddressId())).toString(), responseEntity.getHeaders().get("Location").get(0));

    }

    @Test
    public void getByIdOk() throws AddressNotExistException
    {
        when(addressService.getAddressById(anyInt())).thenReturn(aAddress());

        ResponseEntity<Address>addressResponseEntity = addressController.getAddressById(anyInt());

        assertEquals(HttpStatus.OK, addressResponseEntity.getStatusCode());
        assertEquals(aAddress().getAddressId(),addressResponseEntity.getBody().getAddressId());
    }

    @Test
    public void getAddressByIdException() throws AddressNotExistException {
        when(addressService.getAddressById(anyInt())).thenThrow(new AddressNotExistException());

        assertThrows(AddressNotExistException.class, () -> {addressController.getAddressById(anyInt());});
    }

    @Test
    public void getAllAddressOK()
    {
        when(addressService.getAll(any(Pageable.class))).thenReturn(anAddressPage());

        ResponseEntity<List<Address>>responseEntity = addressController.getAllAddress(aPageable());

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(anAddressPage().getTotalElements(), Long.valueOf(responseEntity.getHeaders().get("X-Total-Pages").get(0)));
        assertEquals(anAddressPage().getContent().get(0).getAddressId(),responseEntity.getBody().get(0).getAddressId());
    }

    @Test
    public void getAllAddressNotContent()
    {
        Pageable pageable = PageRequest.of(5,10);
        Page<Address>addressPage = mock(Page.class);

        when(addressPage.getContent()).thenReturn(Collections.emptyList());
        when(addressService.getAll(pageable)).thenReturn(addressPage);

        ResponseEntity<List<Address>>listResponseEntity = addressController.getAllAddress(pageable);

        assertEquals(HttpStatus.NO_CONTENT, listResponseEntity.getStatusCode());
        assertEquals(0,listResponseEntity.getBody().size());
    }
}
*/