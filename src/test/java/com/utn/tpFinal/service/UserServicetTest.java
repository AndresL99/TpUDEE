package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Residence;
import com.utn.tpFinal.domain.User;
import com.utn.tpFinal.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.utn.tpFinal.Utils.TestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServicetTest
{
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp()
    {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    public void getUserNameAndPasswordOk()
    {
        String username = "client";
        String password = "1234";

        when(userRepository.findByUsernameAndPassword(username,password)).thenReturn(aUser());

        User user = userService.getUsernameAndPassword(username,password);

        assertEquals(aUser(),user);
    }

    @Test
    public void getAllUserTestOk()
    {
        when(userRepository.findAll(any(Pageable.class))).thenReturn(aUserPage());

        Page<User> page = userService.getAll(aPageable());

        assertEquals(aResidencePage().getTotalElements(),page.getTotalElements());
        assertEquals(aUserPage().getContent().get(0).getUsername(),page.getContent().get(0).getUsername());

        verify(userRepository,times(1)).findAll(aPageable());
    }

}
