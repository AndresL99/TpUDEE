package com.utn.tpFinal.controller.backoffice;


import com.utn.tpFinal.controller.TariffController;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.domain.dto.UserDTO;
import com.utn.tpFinal.exception.TariffExistException;
import com.utn.tpFinal.exception.TariffNotExistException;
import com.utn.tpFinal.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RestController
@RequestMapping("/Back/Tariff")
public class TariffBackController {

    /**
     * 2) Alta, baja y modificación de tarifas.
     */

    private TariffController tariffController;
    private ConversionService conversionService;

    @Autowired
    public TariffBackController(TariffController tariffController, ConversionService conversionService) {
        this.tariffController = tariffController;
        this.conversionService = conversionService;

    }
    // get all
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Tariff>> getAllTariff(Pageable pageable) {
        Page page = tariffController.getAll(pageable);
        return response(page);
    }

    //add one
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @PostMapping(consumes = "application/json")
    public ResponseEntity newTariff(@RequestBody Tariff tariff) throws TariffExistException
    {
        Tariff t = tariffController.addTariff(tariff);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{tariffId}")
                .buildAndExpand("Tariff"+t.getTariffId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    //get one
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @GetMapping(value = "{tariffId}", produces = "application/json")
    public ResponseEntity<Tariff> getTariffById(@PathVariable("tariffId") Integer tariffId) throws TariffNotExistException
    {
        Tariff tariff = tariffController.getTariffById(tariffId);
        return ResponseEntity.ok(tariff);
    }
    //update
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    @PutMapping("/tariffId")
    public ResponseEntity updateTariff(@PathVariable Integer tariffId,
                                       @RequestBody Tariff tariff)
    {
        tariffController.update(tariffId, tariff);
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
