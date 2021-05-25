package com.utn.tpFinal.controller;

import com.utn.tpFinal.domain.Meter;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.service.MeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/Meter")
public class MeterController {

    private MeterService meterService;

    @Autowired
    public MeterController(MeterService meterService) {
        this.meterService = meterService;
    }

    @PostMapping
    public PostResponse addMeter(@RequestBody Meter newMeter){
        return meterService.addMeter(newMeter);
    }

    @GetMapping
    public List<Meter> getAll(){
        return meterService.getAll();
    }

    @GetMapping("/{meterId}")
    public Meter getMeterBySerialNumber(@PathVariable Integer meterId){
        return meterService.getMeterBySerialNumber(meterId);
    }
    @DeleteMapping("/{meterId}")
    public void deleteBySerialNumber(@PathVariable Integer meterId){

        meterService.deleteBySerialNumber(meterId);
    }
}
