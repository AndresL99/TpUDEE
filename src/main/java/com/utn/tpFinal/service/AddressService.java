package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Address;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.exception.AddressExistException;
import com.utn.tpFinal.exception.AddressNotExistException;
import com.utn.tpFinal.exception.TariffExistException;
import com.utn.tpFinal.repository.AddressRepository;
import com.utn.tpFinal.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class AddressService {

    private AddressRepository addressRepository;
    private static final String ADDRESS_PATH ="Address";

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address addAddress(Address newAddress)throws AddressExistException
    {
        if (!addressRepository.existsById(newAddress.getAddressId()))
        {
            return addressRepository.save(newAddress);
        }
        else
        {
            throw new AddressExistException();
        }
    }


    public Address getAddressById(Integer idAddress) throws AddressNotExistException
    {
        return addressRepository.findById(idAddress)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public void deleteAddressById(Integer idAddress) {
        addressRepository.deleteById(idAddress);
    }

    public Page<Address> getAll(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }
}
