package com.utn.tpFinal.controller;

import com.utn.tpFinal.domain.Measurement;
import com.utn.tpFinal.domain.Meter;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.exception.TariffExistException;
import com.utn.tpFinal.exception.TariffNotExistException;
import com.utn.tpFinal.service.MeterService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/Meter")
public class MeterController {

    private MeterService meterService;

    @Autowired
    public MeterController(MeterService meterService) {
        this.meterService = meterService;
    }


    public Meter addMeter(Meter meter)
    {
       return meterService.addMeter(meter);
    }


    public Meter getMeterById( Integer meterId)
    {
       return meterService.getMeterById(meterId)
    }

    public Page getAllMeter(Pageable pageable) {
        return  this.meterService.getAllMeter(pageable);
    }


    public void deleteMeterById(Integer idMeter) {
        meterService.deleteById(idMeter);
    }

    public void update(Integer meterId, Meter meter) {
        meterService.update(meterId,meter);
    }





}
