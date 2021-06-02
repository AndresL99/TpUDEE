package com.utn.tpFinal.controller;


import com.utn.tpFinal.domain.Address;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.domain.dto.AddressDto;
import com.utn.tpFinal.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public PostResponse addAddress(@RequestBody Address newAddress){
        return addressService.addAddress(newAddress);
    }

    @GetMapping
    public List<Address> getAll(){
        return addressService.getAll();
    }

    @GetMapping("/{idAddress}")
    public AddressDto getAddressById(@PathVariable Integer idAddress){
        return conversionService.convert(addressService.getAddressById(idAddress),AddressDto.class);
    }
    @DeleteMapping("/{idAddress}")
    public void deleteAddressById(@PathVariable Integer idAddress){
        addressService.deleteAddressById(idAddress);
    }


}
