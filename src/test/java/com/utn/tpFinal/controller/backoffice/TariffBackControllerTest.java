package com.utn.tpFinal.controller.backoffice;

import com.utn.tpFinal.controller.TariffController;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.domain.dto.UserDTO;
import com.utn.tpFinal.exception.TariffExistException;
import com.utn.tpFinal.exception.TariffNotExistException;
import com.utn.tpFinal.utils.EntityURLBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.ConversionService;
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

public class TariffBackControllerTest
{
    private TariffController tariffController;
    private ConversionService conversionService;
    private TariffBackController tariffBackController;
    private UserDTO backOffice;
    Authentication authentication;

    @BeforeEach
    public void setUp()
    {
        tariffController = mock(TariffController.class);
        authentication = mock(Authentication.class);
        conversionService = mock(ConversionService.class);
        backOffice = aBackOffice();
        tariffBackController = new TariffBackController(tariffController,conversionService);
    }

    @Test
    public void newTariff()
    {
        try
        {
            authentication = mock(Authentication.class);
            MockHttpServletRequest request = new MockHttpServletRequest();
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            when(authentication.getPrincipal()).thenReturn(backOffice);
            when(tariffController.addTariff(aTariff())).thenReturn(aTariff());

            ResponseEntity response = tariffBackController.newTariff(authentication,aTariff());

            assertEquals(HttpStatus.CREATED.value(),
                    response.getStatusCodeValue());

            assertEquals(EntityURLBuilder.buildURL("Tariff", String.valueOf(aTariff().getTariffId())).toString(),
                    response.getHeaders().get("Location").get(0));
        }
        catch (TariffExistException ex)
        {
            ex.getMessage();
        }
    }

    /*@Test
    public void newTariffForbidden() throws TariffExistException {
        when(authentication.getPrincipal()).thenReturn(aClientUser());
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        tariffBackController.newTariff(authentication,aTariff());

        assertEquals(HttpStatus.FORBIDDEN.value(),HttpStatus.FORBIDDEN.value());


    }*/

    @Test
    public void getAll()
    {

        when(authentication.getPrincipal()).thenReturn(backOffice);
        when(tariffController.getAll(any(Pageable.class))).thenReturn(aTariffPage());

        ResponseEntity<List<Tariff>> response = tariffBackController.getAllTariff(authentication,aPageable());


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(aTariffPage().getContent().get(0).getTariffName(), response.getBody().get(0).getTariffName());
    }

    @Test
    public void getAllNotContent()
    {
        Pageable pageable = PageRequest.of(5,10);
        Page<Tariff>pageM = mock(Page.class);
        authentication = mock(Authentication.class);

        when(authentication.getPrincipal()).thenReturn(backOffice);
        when(pageM.getContent()).thenReturn(Collections.emptyList());
        when(tariffController.getAll(pageable)).thenReturn(pageM);

        ResponseEntity<List<Tariff>>responseEntity = tariffBackController.getAllTariff(authentication,pageable);

        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
        assertEquals(0,responseEntity.getBody().size());
    }

    @Test
    public void getByIdTariffOk()
    {
        try
        {
            authentication = mock(Authentication.class);
            when(authentication.getPrincipal()).thenReturn(backOffice);
            when(tariffController.getTariffById(10)).thenReturn(aTariff());

            ResponseEntity<Tariff>responseEntity = tariffBackController.getTariffById(authentication,10);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(aTariff().getTariffName(), responseEntity.getBody().getTariffName());
        }
        catch(TariffNotExistException e)
        {
            fail();
        }
    }

    @Test
    public void getByIdTariffException() throws TariffNotExistException
    {
        authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(backOffice);
        when(tariffController.getTariffById(67)).thenThrow(new TariffNotExistException());
        assertThrows(TariffNotExistException.class, () -> {tariffBackController.getTariffById(authentication,67);});
    }

    @Test
    public void updateOK()
    {
        authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(backOffice);
        ResponseEntity entity = tariffBackController.updateTariff(authentication,aTariff().getTariffId(),aTariff());

        assertEquals(HttpStatus.ACCEPTED.value(),entity.getStatusCodeValue());

        verify(tariffController,times(1)).update(aTariff().getTariffId(),aTariff());
    }

}
