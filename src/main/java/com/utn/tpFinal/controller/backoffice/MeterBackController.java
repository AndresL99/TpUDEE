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

@Controller
@RestController
@RequestMapping("/Back/Meter")
public class MeterBackController {

    /**
     * 3) Alta, baja y modificación de domicilios y medidores.
     */


    MeterController meterController;

    @Autowired
    public MeterBackController(MeterController meterController) {
        this.meterController = meterController;
    }

    //addmeter
    @PostMapping()
    public ResponseEntity addMeter(Authentication authentication,@RequestBody Meter meter) throws MeterExistException
    {
        verifyAuthBackOffice(authentication);
        Meter m = meterController.addMeter(meter);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{meterId}")
                .buildAndExpand("Meter/"+m.getMeterId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    //getAll
    @GetMapping()
    public ResponseEntity<List<Meter>> getAllMeters(Authentication authentication,Pageable pageable)
    {
        verifyAuthBackOffice(authentication);
        Page page =meterController.getAllMeter(pageable);
        return response(page);
    }
    // get one
    @GetMapping( "{meterId}")
    public ResponseEntity<Meter> getMeterById(Authentication authentication,@PathVariable("meterId") Integer meterid) throws MeterNotExistException
    {
        verifyAuthBackOffice(authentication);
        Meter meter = meterController.getMeterById(meterid);
        return ResponseEntity.ok(meter);
    }

    //delete
    @DeleteMapping("/{idMeter}")
    public void deleteMeterById(Authentication authentication,@PathVariable Integer idMeter)
    {
        verifyAuthBackOffice(authentication);
        meterController.deleteMeterById(idMeter);
    }
    //update
    @PutMapping("/meterId")
    public ResponseEntity updateMeter(Authentication authentication,@PathVariable Integer meterId,
                                        @RequestBody Meter meter)
    {
        verifyAuthBackOffice(authentication);
        meterController.update(meterId,meter);
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
