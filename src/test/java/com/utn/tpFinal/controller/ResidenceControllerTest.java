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

import static com.utn.tpFinal.Utils.TestUtils.aMeterJson;
import static com.utn.tpFinal.Utils.TestUtils.aResidenceJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ResidenceController.class)
public class ResidenceControllerTest extends AbstractControllerTest
{
    @Mock
    ResidenceService residenceService;

    ResidenceController residenceController;

    @BeforeEach
    public void setUp()
    {
        residenceService = mock(ResidenceService.class);
        residenceController = new ResidenceController(residenceService);
    }

    @Test
    public void getAllResidence() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .get("/residence")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
    }

    @Test
    public void getResidenceById() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .get("/residence/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
    }

    @Test
    public void addResidence() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .post("/residence")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aResidenceJson()))
                .andExpect(status().isOk());

        assertEquals(HttpStatus.OK.value(), resultActions.andReturn().getResponse().getStatus());
    }

    @Test
    public void addResidenceBadRequest() throws Exception {
        final ResultActions resultActions = givenController().perform(MockMvcRequestBuilders
                .post("/residence")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        assertEquals(HttpStatus.BAD_REQUEST.value(), resultActions.andReturn().getResponse().getStatus());
    }
}
