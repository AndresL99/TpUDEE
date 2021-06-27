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
import org.springframework.security.access.prepost.PreAuthorize;
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
    //get all
    public Page<Tariff> getAll(Pageable pageable) { return this.tariffService.getAllTariff(pageable);}
    //add
    public Tariff addTariff(@RequestBody Tariff tariff) throws TariffExistException
    {return tariffService.addTariff(tariff);}
    //get one
    public Tariff getTariffById(@PathVariable Integer tariffId) throws TariffNotExistException {
        return tariffService.getTariffById(tariffId); }
    //update
    public void update( @PathVariable Integer tariffId, @RequestBody Tariff tariff) {
        tariffService.update(tariffId,tariff);
    }
}
