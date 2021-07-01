package com.utn.tpFinal.controller.backoffice;


import com.utn.tpFinal.controller.AddressController;
import com.utn.tpFinal.controller.MeterController;
import com.utn.tpFinal.domain.Address;
import com.utn.tpFinal.domain.Meter;
import com.utn.tpFinal.domain.dto.UserDTO;
import com.utn.tpFinal.exception.AddressExistException;
import com.utn.tpFinal.exception.AddressNotExistException;
import com.utn.tpFinal.exception.MeterExistException;
import com.utn.tpFinal.exception.MeterNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/Back/Address")
public class AddressBackController {

    /**
     *3) Alta, baja y modificación de domicilios y medidores.
     */

    AddressController addressController;


    @Autowired
    public AddressBackController(AddressController addressController) {
        this.addressController = addressController;

    }
    //add
    @PostMapping(consumes = "application/json")
    public ResponseEntity addAddress(Authentication authentication,@RequestBody Address address) throws AddressExistException
    {
        verifyAuthBackOffice(authentication);
        Address a = addressController.addAddress(address);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{addressId}")
                .buildAndExpand("Address/"+a.getAddressId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    //get all
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Address>> getAllAddress(Authentication authentication,Pageable pageable)
    {
        verifyAuthBackOffice(authentication);
        Page page = addressController.getAll(pageable);
        return response(page);
    }
    // get one
    @GetMapping(value = "/{addressId}", produces = "application/json")
    public ResponseEntity<Address> getAddressById(Authentication authentication,@PathVariable("addressId") Integer addressId) throws AddressNotExistException
    {
        verifyAuthBackOffice(authentication);
        Address address = addressController.getAddressById(addressId);
        return ResponseEntity.ok(address); }
    //delete
    @DeleteMapping("/{idAddress}")
    public void deleteAddressById(Authentication authentication,@PathVariable Integer idAddress)
    {
        verifyAuthBackOffice(authentication);
        addressController.deleteAddressById(idAddress);
    }
    //update
    @PutMapping("/addressId")
    public ResponseEntity updateAddress(Authentication authentication,@PathVariable Integer addressId,
                                        @RequestBody Address address)
    {
        verifyAuthBackOffice(authentication);
        addressController.update(addressId,address);
        return ResponseEntity.accepted().build();
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



    private void verifyAuthBackOffice(Authentication authentication)
    {
        if(!((UserDTO) authentication.getPrincipal()).getAdmin())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access forbidden for your profile.");
    }



}
