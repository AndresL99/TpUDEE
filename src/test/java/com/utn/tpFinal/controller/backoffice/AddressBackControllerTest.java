package com.utn.tpFinal.controller.backoffice;

import com.utn.tpFinal.controller.AddressController;
import com.utn.tpFinal.controller.MeterController;
import com.utn.tpFinal.domain.Address;
import com.utn.tpFinal.domain.dto.UserDTO;
import com.utn.tpFinal.exception.AddressExistException;
import com.utn.tpFinal.exception.AddressNotExistException;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;

import static com.utn.tpFinal.Utils.TestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class AddressBackControllerTest
{
    private AddressController addressController;
    private MeterController meterController;
    private AddressBackController addressBackController;
    Authentication authentication;
    private UserDTO backOffice;

    @BeforeEach
    public void setUp()
    {
        authentication = mock(Authentication.class);
        backOffice = aBackOffice();
        addressController = mock(AddressController.class);
        meterController = mock(MeterController.class);
        addressBackController = new AddressBackController(addressController,meterController);
    }

    @Test
    public void addAddressTestOk()
    {
        try
        {
            authentication = mock(Authentication.class);
            MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

            when(authentication.getPrincipal()).thenReturn(backOffice);
            when(addressController.addAddress(aAddress())).thenReturn(aAddress());

            ResponseEntity responseEntity = addressBackController.addAddress(authentication,aAddress());

            assertEquals(HttpStatus.CREATED.value(),
                    responseEntity.getStatusCodeValue());

            assertEquals(EntityURLBuilder.buildURL("Address", String.valueOf(aAddress().getAddressId())).toString(),
                    responseEntity.getHeaders().get("Location").get(0));
        }
        catch (AddressExistException ex)
        {
            fail();
        }
    }

    @Test
    public void getByIdOk()
    {
        try
        {
            authentication = mock(Authentication.class);
            when(authentication.getPrincipal()).thenReturn(backOffice);
            when(addressController.getAddressById(10)).thenReturn(aAddress());

            ResponseEntity<Address>responseEntity = addressBackController.getAddressById(authentication,10);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(aAddress().getAddressId(), responseEntity.getBody().getAddressId());
        }
        catch (AddressNotExistException e)
        {
            fail();
        }
    }

    @Test
    public void getByIdException() throws AddressNotExistException {
        authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(backOffice);
        when(addressController.getAddressById(10)).thenThrow(new AddressNotExistException());
        assertThrows(AddressNotExistException.class, () -> {addressBackController.getAddressById(authentication,10);});
    }

    @Test
    public void getAllOk()
    {
        Pageable pageable = PageRequest.of(5,10);
        Page<Address> pageM = mock(Page.class);
        authentication = mock(Authentication.class);

        when(authentication.getPrincipal()).thenReturn(backOffice);
        when(pageM.getContent()).thenReturn(Collections.emptyList());
        when(addressController.getAll(pageable)).thenReturn(pageM);

        ResponseEntity<List<Address>>responseEntity = addressBackController.getAllAddress(authentication,pageable);

        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
        assertEquals(0,responseEntity.getBody().size());
    }

    @Test
    public void getAllNoContent()
    {
        Pageable pageable = PageRequest.of(5,10);
        Page<Address>pageM = mock(Page.class);
        authentication = mock(Authentication.class);

        when(authentication.getPrincipal()).thenReturn(backOffice);
        when(pageM.getContent()).thenReturn(Collections.emptyList());
        when(addressController.getAll(pageable)).thenReturn(pageM);

        ResponseEntity<List<Address>>responseEntity = addressBackController.getAllAddress(authentication,pageable);

        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
        assertEquals(0,responseEntity.getBody().size());
    }

    @Test
    public void updateAddressOk()
    {
        authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(backOffice);
        ResponseEntity entity = addressBackController.updateAddress(authentication,aAddress().getAddressId(),aAddress());

        assertEquals(HttpStatus.ACCEPTED.value(),entity.getStatusCodeValue());

        verify(addressController,times(1)).update(aAddress().getAddressId(),aAddress());
    }

    @Test
    public void deleteAddressOk()
    {
        when(authentication.getPrincipal()).thenReturn(backOffice);
        doNothing().when(addressController).deleteAddressById(11);
        addressBackController.deleteAddressById(authentication,11);
        verify(addressController,times(1)).deleteAddressById(11);
    }
}
