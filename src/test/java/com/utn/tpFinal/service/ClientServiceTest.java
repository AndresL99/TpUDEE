package com.utn.tpFinal.service;

import com.utn.tpFinal.repository.ClientRepository;
import com.utn.tpFinal.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ClientServiceTest
{
    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;
    private ModelMapper modelMapper;
    private UserRepository userRepository;

    @BeforeEach
    public void setUp()
    {
        clientRepository = mock(ClientRepository.class);
        userRepository = mock(UserRepository.class);
        modelMapper = mock(ModelMapper.class);
        clientService = new ClientService(clientRepository,modelMapper,userRepository);
    }

}
