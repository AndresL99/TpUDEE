package com.utn.tpFinal.controller;

import com.utn.tpFinal.AbstractControllerTest;
import com.utn.tpFinal.service.MeterService;
import com.utn.tpFinal.service.ResidenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
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



}
