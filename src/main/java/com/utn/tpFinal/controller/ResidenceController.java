package com.utn.tpFinal.controller;

import com.utn.tpFinal.domain.Residence;
import com.utn.tpFinal.exception.ResidenceExistException;
import com.utn.tpFinal.exception.ResidenceNotExistException;
import com.utn.tpFinal.exception.TariffNotExistException;
import com.utn.tpFinal.service.ResidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/Residence")
public class ResidenceController {

    private ResidenceService residenceService;

    @Autowired
    public ResidenceController(ResidenceService residenceService) {
        this.residenceService = residenceService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity newResidence(@RequestBody Residence residence)throws ResidenceExistException
    {
        Residence r = residenceService.addResidence(residence);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{residenceId}")
                .buildAndExpand("Residence"+r.getResidenceId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "{residenceId}", produces = "application/json")
    public ResponseEntity<Residence> getResidenceById(@PathVariable("residenceId") Integer residenceId) throws ResidenceNotExistException
    {
        Residence residence = residenceService.getResidenceById(residenceId);
        return ResponseEntity.ok(residence);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Residence>> allResidence(Pageable pageable) {
        Page page = residenceService.getAllResidence(pageable);
        return response(page);
    }

    @DeleteMapping("/{residenceId}")
    public void deleteResidenceById(@PathVariable Integer residenceId){

        residenceService.deleteResidenceById(residenceId);
    }

    @PutMapping("/{residenceId}/Meter/{meterId}")
    public void addMeter(@PathVariable Integer residenceId, @PathVariable Integer meterId) throws ResidenceNotExistException {
         residenceService.addMeter(residenceId, meterId);

    }
    @PutMapping("/{residenceId}/Tariff/{tariffId}")
    public void addTariff(@PathVariable Integer residenceId, @PathVariable Integer tariffId) throws ResidenceNotExistException, TariffNotExistException {
        residenceService.addTariff(residenceId, tariffId);
    }

    /*public Page getResidenceByUser(Integer idClient, Pageable pageable)  {
        return  this.residenceService.getResidenceByClientId(idClient,pageable);
    }
    public Residence getResidenceBYMeter(Integer meterId, Pageable pageable){
        return this.residenceService.getResidenceByMeterId(meterId,pageable);
    }*/



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
