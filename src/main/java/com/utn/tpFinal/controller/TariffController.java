package com.utn.tpFinal.controller;

import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.domain.dto.TariffDto;
import com.utn.tpFinal.exception.TariffExistException;
import com.utn.tpFinal.exception.TariffNotExistException;
import com.utn.tpFinal.service.TariffService;
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
@RequestMapping("/Tariff")
public class TariffController {

    private TariffService tariffService;


    private ConversionService conversionService;

    @Autowired
    public TariffController(TariffService tariffService, ConversionService conversionService) {
        this.tariffService = tariffService;
        this.conversionService = conversionService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity newTariff(@RequestBody Tariff tariff) throws TariffExistException
    {
        Tariff t = tariffService.addTariff(tariff);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{tariffId}")
                .buildAndExpand(t.getTariffId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "{tariffId}", produces = "application/json")
    public ResponseEntity<Tariff> getTariffById(@PathVariable("tariffId") Integer tariffId) throws TariffNotExistException
    {
        Tariff tariff = tariffService.getTariffById(tariffId);
        return ResponseEntity.ok(tariff);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Tariff>> getAllTariff(Pageable pageable) {
        Page page = tariffService.getAllTariff(pageable);
        return response(page);
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
