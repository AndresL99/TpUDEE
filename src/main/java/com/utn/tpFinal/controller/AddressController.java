package com.utn.tpFinal.controller;


import com.utn.tpFinal.domain.Address;
import com.utn.tpFinal.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/Address")
public class AddressController {


    private AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public void addAddress(@RequestBody Address newAddress){
        addressService.addAddress(newAddress);
    }

    @GetMapping
    public List<Address> getAll(){
        return addressService.getAll();
    }

    @GetMapping("/{idAddress}")
    public Address getAddressById(@PathVariable Integer idAddress){

        return addressService.getAddressById(idAddress);
    }
    @DeleteMapping("/{idAddress}")
    public void deleteAddressById(@PathVariable Integer idAddress){
        addressService.deleteAddressById(idAddress);
    }


}
