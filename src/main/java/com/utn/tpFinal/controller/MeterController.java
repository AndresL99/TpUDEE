package com.utn.tpFinal.controller;

import com.utn.tpFinal.domain.Meter;
import com.utn.tpFinal.service.MeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return meterService.getMeterById(meterId);
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
