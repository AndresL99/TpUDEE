package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Address;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.repository.AddressRepository;
import com.utn.tpFinal.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
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

    public PostResponse addAddress(Address newAddress){

        Address a = addressRepository.save(newAddress);
        return PostResponse
                .builder()
                .status(HttpStatus.CREATED)
                .url(EntityURLBuilder.buildURL(ADDRESS_PATH, a.getAddressId().toString()))
                .build();
    }


    public Address getAddressById(Integer idAddress) {
        return addressRepository.findById(idAddress)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public void deleteAddressById(Integer idAddress) {
        addressRepository.deleteById(idAddress);
    }

    public List<Address> getAll() {
        return addressRepository.findAll();
    }
}
