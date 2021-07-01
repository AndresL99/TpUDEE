package com.utn.tpFinal.controller;

import com.utn.tpFinal.domain.Measurement;
import com.utn.tpFinal.domain.dto.MeasurementSenderDTO;
import com.utn.tpFinal.exception.MeterNotExistException;
import com.utn.tpFinal.exception.ResidenceNotExistException;
import com.utn.tpFinal.service.MeasurementService;
import com.utn.tpFinal.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/measurements")
public class MeasurementController
{
    private MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    /*@PostMapping
    public ResponseEntity<String>add(@RequestBody MeasurementSenderDTO measurementSenderDTO) throws MeterNotExistException, ResidenceNotExistException {
        Measurement m = measurementService.add(measurementSenderDTO,measurementSenderDTO.getSerialNumber(),measurementSenderDTO.getPassword());

        return ResponseEntity.status(HttpStatus.CREATED).location(EntityURLBuilder.buildURL("measurements",measurementSenderDTO.getSerialNumber())).contentType(MediaType.APPLICATION_JSON)
                .body("");
    }*/

    /*@PostMapping
    public ResponseEntity addMeasurement (@RequestBody MeasurementSenderDTO dto ) throws ParseException, ResidenceNotExistException, MeterNotExistException {

        System.out.println(dto.toString());
        Measurement measure = Measurement.builderM(dto);
        measurementService.add(measure,dto.getSerialNumber(),dto.getPassword());

        return ResponseEntity.ok().build();
    }*/

}
