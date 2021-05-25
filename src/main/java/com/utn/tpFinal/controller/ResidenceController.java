package com.utn.tpFinal.controller;

import com.utn.tpFinal.domain.Residence;
import com.utn.tpFinal.service.ResidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/Residence")

public class ResidenceController {

    private ResidenceService residenceService;

    @Autowired
    public ResidenceController(ResidenceService residenceService) {
        this.residenceService = residenceService;
    }

    @PostMapping
    public void addResidence(@RequestBody Residence newResidence){
        residenceService.addResidence(newResidence);
    }

    @GetMapping("/{residenceId}")
    public Residence getResidenceById(@PathVariable Integer residenceId){

        return residenceService.getResidenceById(residenceId);
    }
    @DeleteMapping("/{residenceId}")
    public void deleteResidenceById(@PathVariable Integer residenceId){

        residenceService.deleteResidenceById(residenceId);
    }


}
