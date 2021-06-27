package com.utn.tpFinal.controller;

import com.utn.tpFinal.AbstractControllerTest;
import com.utn.tpFinal.domain.Invoice;
import com.utn.tpFinal.domain.Residence;
import com.utn.tpFinal.exception.InvoiceNotExistExpection;
import com.utn.tpFinal.exception.ResidenceExistException;
import com.utn.tpFinal.exception.ResidenceNotExistException;
import com.utn.tpFinal.exception.TariffNotExistException;
import com.utn.tpFinal.service.MeterService;
import com.utn.tpFinal.service.ResidenceService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ResidenceControllerTest extends AbstractControllerTest
{

    private ResidenceService residenceService;

    private ResidenceController residenceController;

    @BeforeEach
    public void setUp()
    {
        residenceService = mock(ResidenceService.class);
        residenceController = new ResidenceController(residenceService);
    }

    @Test
    public void addResidenceTestOk() throws ResidenceExistException {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

        when(residenceService.addResidence(aResidence())).thenReturn(aResidence());

        ResponseEntity responseEntity = residenceController.newResidence(aResidence());

        assertEquals(HttpStatus. CREATED.value(),responseEntity.getStatusCodeValue());
        assertEquals(EntityURLBuilder.buildURL("Residence",String.valueOf(aResidence().getResidenceId())).toString(), responseEntity.getHeaders().get("Location").get(0));
    }

    @Test
    public void getByIdOk() throws ResidenceNotExistException {
        Integer id_residence = aResidence().getResidenceId();
        when(residenceService.getResidenceById(id_residence)).thenReturn(aResidence());

        ResponseEntity<Residence>responseEntity = residenceController.getResidenceById(id_residence);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(aResidence().getResidenceId(),responseEntity.getBody().getResidenceId());
    }

    @Test
    public void getByIdException() throws ResidenceNotExistException {
        when(residenceService.getResidenceById(anyInt())).thenThrow(new ResidenceNotExistException());

        assertThrows(ResidenceNotExistException.class, ()-> {residenceController.getResidenceById(anyInt());});
    }

    @Test
    public void getAllTestOk()
    {
        when(residenceService.getAllResidence(any(Pageable.class))).thenReturn(aResidencePage());

        ResponseEntity<List<Residence>>listResponseEntity = residenceController.allResidence(aPageable());

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
        assertEquals(aResidencePage().getTotalElements(),Long.valueOf(listResponseEntity.getHeaders().get("X-Total-Pages").get(0)));
        assertEquals(aResidencePage().getContent().get(0).getResidenceId(),listResponseEntity.getBody().get(0).getResidenceId());
    }

    @Test
    public void getAllTestNoContent()
    {
        Pageable pageable = PageRequest.of(5,10);
        Page<Residence> residencePage = mock(Page.class);

        when(residencePage.getContent()).thenReturn(Collections.emptyList());
        when(residenceService.getAllResidence(pageable)).thenReturn(residencePage);

        ResponseEntity<List<Residence>>responseEntity = residenceController.allResidence(pageable);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(0,responseEntity.getBody().size());
    }

    @Test
    public void deleteResidenceByIdTestOk()
    {
        Integer id_residence = aResidence().getResidenceId();
        doNothing().when(residenceService).deleteResidenceById(id_residence);
        residenceController.deleteResidenceById(id_residence);
        verify(residenceService,times(1)).deleteResidenceById(id_residence);
    }

}
