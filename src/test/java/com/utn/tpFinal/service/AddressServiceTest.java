package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Address;
import com.utn.tpFinal.exception.AddressExistException;
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

import java.util.Optional;

import static com.utn.tpFinal.Utils.TestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


public class AddressServiceTest
{
    private AddressService addressService;
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

    @Test
    public void addAddressOk() throws AddressExistException {
        when(addressRepository.save(aAddress())).thenReturn(aAddress());

        Address address = addressService.addAddress(aAddress());

        assertEquals(aAddress().getAddressId(),address.getAddressId());
    }

    @Test
    public void updateAddress()
    {
        when(addressRepository.findById(anyInt())).thenReturn(Optional.of(aAddress()));
        when(addressRepository.save(aAddress())).thenReturn(aAddress());

        addressService.update(10,aAddress());

        verify(addressRepository,times(1)).findById(aAddress().getAddressId());
        verify(addressRepository,times(1)).save(aAddress());
    }

    @Test
    public void deleteAddressOk()
    {
        when(addressRepository.existsById(10)).thenReturn(true);
        addressService.deleteAddressById(10);
        verify(addressRepository,times(1)).deleteById(10);
    }

}
