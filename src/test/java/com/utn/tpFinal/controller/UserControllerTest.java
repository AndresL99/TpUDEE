package com.utn.tpFinal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.tpFinal.AbstractControllerTest;
import com.utn.tpFinal.domain.dto.LoginRequestDTO;
//import com.utn.tpFinal.domain.dto.LoginResponseDTO;
import com.utn.tpFinal.domain.dto.LoginResponseDTO;
import com.utn.tpFinal.domain.dto.UserDTO;
import com.utn.tpFinal.exception.InvalidUserException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.utn.tpFinal.Utils.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends AbstractControllerTest
{

    private UserService userService;
    private UserController userController;
    private ModelMapper modelMapper;
    private ObjectMapper objectMapper;
    private LoginRequestDTO loginRequestDTO;

     @BeforeEach
     public void setUp()
     {
         userService = mock(UserService.class);
         modelMapper = mock(ModelMapper.class);
         objectMapper = mock(ObjectMapper.class);
         userController = new UserController(userService,modelMapper,objectMapper);
         this.loginRequestDTO = LoginRequestDTO.builder().username("admin").password("1234").build();
     }

    @Test
    public void loginOk()
    {
        when(userService.getUsernameAndPassword(loginRequestDTO.getUsername(),loginRequestDTO.getPassword())).thenReturn(aUser());
        when(modelMapper.map(aUser(), UserDTO.class)).thenReturn(anUserDTO());

        ResponseEntity<LoginResponseDTO>responseEntity = userController.login(loginRequestDTO);
        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
    }

    @Test
    public void loginUnauthorized()
    {
        when(userService.getUsernameAndPassword(loginRequestDTO.getUsername(),loginRequestDTO.getPassword())).thenReturn(null);
        ResponseEntity<LoginResponseDTO> responseEntity =userController.login(loginRequestDTO);
        assertEquals(HttpStatus.UNAUTHORIZED.value(),responseEntity.getStatusCodeValue());
    }

}
