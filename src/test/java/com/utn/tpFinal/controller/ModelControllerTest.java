package com.utn.tpFinal.controller;

import com.utn.tpFinal.domain.Meter;
import com.utn.tpFinal.domain.Model;
import com.utn.tpFinal.service.ModelService;
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
import static com.utn.tpFinal.Utils.TestUtils.aMeterPage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ModelControllerTest
{
    private ModelService modelService;
    private ModelController modelController;

    @BeforeEach
    public void setUp()
    {
        modelService = mock(ModelService.class);
        modelController = new ModelController(modelService);
    }

    @Test
    public void addModelOk()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(modelService.addModel(aModel())).thenReturn(aModel());

        ResponseEntity responseEntity = modelController.addModel(aModel());

        assertEquals(HttpStatus.CREATED.value(),responseEntity.getStatusCodeValue());
        assertEquals(EntityURLBuilder.buildURL("Model",String.valueOf(aModel().getModelId())).toString(),responseEntity.getHeaders().get("Location").get(0));
    }

    @Test
    public void getByIdOk()
    {
        when(modelService.getModelById(anyInt())).thenReturn(aModel());

        ResponseEntity<Model> response = modelController.getModelById(anyInt());

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(aModel().getModelId(),response.getBody().getModelId());
    }

    @Test
    public void getByIdNotFound()
    {
        when(modelService.getModelById(anyInt())).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(HttpClientErrorException.class, ()-> {modelController.getModelById(anyInt());});
    }

    @Test
    public void getAllOk()
    {
        when(modelService.getAllModel(any(Pageable.class))).thenReturn(aModelPage());

        ResponseEntity<List<Model>>entity = modelController.getAllModel(aPageable());

        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(aModelPage().getTotalElements(),Long.valueOf(entity.getHeaders().get("X-Total-Pages").get(0)));
        assertEquals(aModelPage().getContent().get(0).getModelId(),entity.getBody().get(0).getModelId());
    }

    @Test
    public void getAllNotContent()
    {
        Pageable pageable = PageRequest.of(5,10);
        Page<Model> page = mock(Page.class);

        when(page.getContent()).thenReturn(Collections.emptyList());
        when(modelService.getAllModel(pageable)).thenReturn(page);

        ResponseEntity<List<Model>>responseEntity = modelController.getAllModel(pageable);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(0,responseEntity.getBody().size());
    }


}
