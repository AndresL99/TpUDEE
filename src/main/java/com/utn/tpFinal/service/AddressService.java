package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Address;
import com.utn.tpFinal.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class AddressService {

    private AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public void addAddress(Address newAddress){
        addressRepository.save(newAddress);
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
