package com.utn.tpFinal.controller;

import com.utn.tpFinal.domain.Measurement;
import com.utn.tpFinal.domain.dto.MeasurementSenderDto;
import com.utn.tpFinal.exception.MeterNotExistException;
import com.utn.tpFinal.exception.ResidenceNotExistException;
import com.utn.tpFinal.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Controller
@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @PostMapping()
    public ResponseEntity addMeasurement (@RequestBody MeasurementSenderDto dto ) throws ParseException, ResidenceNotExistException, MeterNotExistException {

            System.out.println(dto.toString());
        Measurement measure = Measurement.builderM(dto);
        measurementService.add(measure,dto.getSerialNumber(),dto.getPassword());

        return ResponseEntity.ok().build();
    }



}
