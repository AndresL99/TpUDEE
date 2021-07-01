package com.utn.tpFinal.controller;


import com.utn.tpFinal.domain.Address;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.domain.dto.AddressDto;
import com.utn.tpFinal.exception.AddressExistException;
import com.utn.tpFinal.exception.AddressNotExistException;
import com.utn.tpFinal.exception.TariffExistException;
import com.utn.tpFinal.exception.TariffNotExistException;
import com.utn.tpFinal.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/Address")
public class AddressController {


    private AddressService addressService;

    private ConversionService conversionService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    public Address addAddress(Address address) throws AddressExistException {
        return addressService.addAddress(address);
    }

    public Page getAll(Pageable pageable) {
        return this.addressService.getAll(pageable);
    }

    public Address getAddressById(Integer addressId) throws AddressNotExistException {
        return addressService.getAddressById(addressId);
    }

    public void deleteAddressById(Integer idAddress) {
        addressService.deleteAddressById(idAddress);
    }


    public void update(Integer addressId, Address address) {
        addressService.update(addressId,address);
    }
}
