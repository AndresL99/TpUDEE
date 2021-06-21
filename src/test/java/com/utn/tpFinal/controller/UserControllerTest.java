package com.utn.tpFinal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.tpFinal.AbstractControllerTest;
import com.utn.tpFinal.domain.dto.LoginRequestDTO;
//import com.utn.tpFinal.domain.dto.LoginResponseDTO;
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

import static com.utn.tpFinal.Utils.TestUtils.aLoginRequestDTO;
import static com.utn.tpFinal.Utils.TestUtils.anUserDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends AbstractControllerTest
{

    private UserService userService;
    private UserController userController;
    private ModelMapper modelMapper;
    private ObjectMapper objectMapper;

     @BeforeEach
     public void setUp()
     {
         initMocks(this);
         userController = new UserController(userService,modelMapper,objectMapper);
     }

    /* @Test
    public void loginClientIsOk()
     {
         try
         {
             when(clientService.login(anyString(),anyString())).thenReturn(anUserDTO());

             ResponseEntity<LoginResponseDTO> loginResponseDTOResponseEntity = userController.login(aLoginRequestDTO());

             assertEquals(HttpStatus.OK,loginResponseDTOResponseEntity.getStatusCode());
             assertNotNull(loginResponseDTOResponseEntity.getBody().getToken());

         }
         catch (InvalidUserException e)
         {
            fail();
         }
     }

     @Test
     public void loginClientNotWithException() throws InvalidUserException {
        when(clientService.login(anyString(),anyString())).thenThrow(new InvalidUserException());

        assertThrows(InvalidUserException.class, ()-> {userController.login(aLoginRequestDTO());});
     }*/

     /*@Test
     public void loginBackOfficeIsOk()
     {
        try
        {
            when(userService.login(anyString(),anyString())).thenReturn(anUserDTO());

            ResponseEntity<LoginResponseDTO>responseEntity = userController.BackOfficelogin(aLoginRequestDTO());

            assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
            assertNotNull(responseEntity.getBody().getToken());
        }
        catch (InvalidUserException e)
        {
           fail();
        }
     }*/

     /*@Test
     public void loginBackOfficeWithException() throws InvalidUserException {
        when(userService.login(anyString(),anyString())).thenThrow(new InvalidUserException());

        assertThrows(InvalidUserException.class, ()-> {userController.BackOfficelogin(aLoginRequestDTO());});
     }*/

}
