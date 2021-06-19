package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Address;
import com.utn.tpFinal.exception.AddressNotExistException;
import com.utn.tpFinal.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.utn.tpFinal.Utils.TestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AddressServiceTest
{
    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    @BeforeEach
    public void setUp()
    {
        addressRepository = mock(AddressRepository.class);
        addressService = new AddressService(addressRepository);
    }

    @Test
    public void getByIdOk() throws AddressNotExistException {
        when(addressRepository.findById(anyInt())).thenReturn(java.util.Optional.of(aAddress()));

        Address address = addressService.getAddressById(anyInt());

        verify(addressRepository,times(1)).findById(anyInt());
    }

    @Test
    public void getByIdException() throws AddressNotExistException {
        when(addressRepository.findById(anyInt())).thenReturn(java.util.Optional.of(aAddress()));

        addressService.getAddressById(anyInt());

        verify(addressRepository,times(1)).findById(anyInt());
    }

    @Test
    public void getAllAddress()
    {
        when(addressRepository.findAll(any(Pageable.class))).thenReturn(anAddressPage());

        Page<Address>addressPage = addressService.getAll(aPageable());

        assertEquals(anAddressPage().getTotalElements(),addressPage.getTotalElements());
        assertEquals(anAddressPage().getContent().get(0).getAddressId(),addressPage.getContent().get(0).getAddressId());

        verify(addressRepository,times(1)).findAll(aPageable());
    }
}
