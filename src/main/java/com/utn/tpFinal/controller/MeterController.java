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

@Controller
@RestController
@RequestMapping("/Meter")
public class MeterController {

    private MeterService meterService;

    @Autowired
    public MeterController(MeterService meterService) {
        this.meterService = meterService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity addMeter(@RequestBody Meter meter)
    {
        Meter m = meterService.addMeter(meter);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{meterId}")
                .buildAndExpand("Meter/"+m.getMeterId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "{meterId}", produces = "application/json")
    public ResponseEntity<Meter> getTariffById(@PathVariable("meterId") Integer meterId)
    {
        Meter meter = meterService.getMeterById(meterId);
        return ResponseEntity.ok(meter);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Meter>> getAllMeter(Pageable pageable) {
        Page page = meterService.getAllMeter(pageable);
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
