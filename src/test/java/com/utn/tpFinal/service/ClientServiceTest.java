package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Client;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.exception.ClientExistException;
import com.utn.tpFinal.exception.ClientNotExistException;
import com.utn.tpFinal.repository.ClientRepository;
import com.utn.tpFinal.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import static com.utn.tpFinal.Utils.TestUtils.aClient;
import static com.utn.tpFinal.Utils.TestUtils.aTariff;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


public class ClientServiceTest
{

    private ClientRepository clientRepository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private ClientService clientService;


    @BeforeEach
    public void setUp()
    {
        clientRepository = mock(ClientRepository.class);
        userRepository = mock(UserRepository.class);
        modelMapper = mock(ModelMapper.class);
        clientService = new ClientService(clientRepository,modelMapper,userRepository);
    }

    @Test
    public void addClientOk() throws ClientExistException
    {
        when(clientRepository.existsById(10)).thenReturn(true);
        when(clientRepository.save(aClient())).thenReturn(aClient());
        Client client = clientService.addClient(aClient());
        assertEquals(aClient().getClientId(),client.getClientId());
        verify(clientRepository,times(1)).save(aClient());
    }

    @Test
    public void getClientByIdOk() throws ClientNotExistException {
        when(clientRepository.findById(10)).thenReturn(java.util.Optional.of(aClient()));

        Client client = clientService.getClientById(10);

        verify(clientRepository,times(1)).findById(10);
    }

    @Test
    public void getByUsernameTest()
    {
        String username = "Client";

        when(clientRepository.findClientByUser_Username(username)).thenReturn(aClient());

        Client actualClient = clientService.getByUserName(username);

        assertEquals(aClient(),actualClient);
    }

    @Test
    public void deleteOk()
    {
        when(clientRepository.existsById(10)).thenReturn(true);
        clientService.deleteById(10);
        verify(clientRepository,times(1)).deleteById(10);
    }

}
