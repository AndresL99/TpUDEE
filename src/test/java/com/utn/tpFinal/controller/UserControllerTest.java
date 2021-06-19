package com.utn.tpFinal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.tpFinal.AbstractControllerTest;
import com.utn.tpFinal.service.ClientService;
import com.utn.tpFinal.service.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = UserController.class)
public class UserControllerTest extends AbstractControllerTest
{
    @Mock
    UserService userService;
    UserController userController;
    ModelMapper modelMapper;
    ObjectMapper objectMapper;
    ClientService clientService;

     @BeforeEach
     public void setUp()
     {
         initMocks(this);
         userController = new UserController(userService,modelMapper,objectMapper, clientService);
     }

     @Test
    public void loginClientIsOk()
     {

     }

     @Test
     public void loginBackOfficeIsOk()
     {

     }



}
