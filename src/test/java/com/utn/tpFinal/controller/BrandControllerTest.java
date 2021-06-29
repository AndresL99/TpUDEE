package com.utn.tpFinal.controller;

import com.utn.tpFinal.domain.Brand;
import com.utn.tpFinal.domain.Model;
import com.utn.tpFinal.service.BrandService;
import com.utn.tpFinal.utils.EntityURLBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;

import static com.utn.tpFinal.Utils.TestUtils.*;
import static com.utn.tpFinal.Utils.TestUtils.aModelPage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BrandControllerTest
{
    private BrandService brandService;
    private BrandController brandController;

    @BeforeEach
    public void setUp()
    {
        brandService = mock(BrandService.class);
        brandController = new BrandController(brandService);
    }

    @Test
    public void addBrandOk()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(brandService.addBrand(aBrand())).thenReturn(aBrand());

        ResponseEntity responseEntity = brandController.addBrand(aBrand());

        assertEquals(HttpStatus.CREATED.value(),responseEntity.getStatusCodeValue());
        assertEquals(EntityURLBuilder.buildURL("Brand",String.valueOf(aBrand().getBrandId())).toString(),responseEntity.getHeaders().get("Location").get(0));
    }


    @Test
    public void getByIdOk()
    {
        when(brandService.getBrandById(anyInt())).thenReturn(aBrand());

        ResponseEntity<Brand> response = brandController.getBrandById(anyInt());

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(aBrand().getBrandId(),response.getBody().getBrandId());
    }

    @Test
    public void getByIdNotFound()
    {
        when(brandService.getBrandById(anyInt())).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(HttpClientErrorException.class, ()-> {brandController.getBrandById(anyInt());});
    }

    @Test
    public void getAll()
    {
        when(brandService.getAll(any(Pageable.class))).thenReturn(aBrandPage());

        ResponseEntity<List<Brand>>entity = brandController.getAllBrand(aPageable());

        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(aModelPage().getTotalElements(),Long.valueOf(entity.getHeaders().get("X-Total-Pages").get(0)));
        assertEquals(aBrandPage().getContent().get(0).getBrandId(),entity.getBody().get(0).getBrandId());
    }

    @Test
    public void getAllNotContent()
    {
        Pageable pageable = PageRequest.of(5,10);
        Page<Brand> page = mock(Page.class);

        when(page.getContent()).thenReturn(Collections.emptyList());
        when(brandService.getAll(pageable)).thenReturn(page);

        ResponseEntity<List<Brand>>responseEntity = brandController.getAllBrand(pageable);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(0,responseEntity.getBody().size());
    }
}
