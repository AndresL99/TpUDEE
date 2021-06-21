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

@Controller
@RestController
@RequestMapping("/Address")
public class AddressController {


    private AddressService addressService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity addAddress(@RequestBody Address address) throws AddressExistException
    {
        Address a = addressService.addAddress(address);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{addressId}")
                .buildAndExpand("Address/"+a.getAddressId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/{addressId}", produces = "application/json")
    public ResponseEntity<Address> getAddressById(@PathVariable("addressId") Integer addressId) throws AddressNotExistException
    {
        Address address = addressService.getAddressById(addressId);
        return ResponseEntity.ok(address);
    }

    @GetMapping(produces = "application/json", value = "/")
    public ResponseEntity<List<Address>> getAllAddress(Pageable pageable) {
        Page page = addressService.getAll(pageable);
        return response(page);
    }

    @DeleteMapping("/{idAddress}")
    public void deleteAddressById(@PathVariable Integer idAddress){
        addressService.deleteAddressById(idAddress);
    }


    private ResponseEntity response(List list, Page page) {
        HttpStatus status = !list.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return ResponseEntity.status(status).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());
    }


    private ResponseEntity response(List list) {
        return ResponseEntity.status(list.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(list);
    }

    private ResponseEntity response(Page page) {
        HttpStatus httpStatus = page.getContent().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.
                status(httpStatus).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());

    }


}
