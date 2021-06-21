package com.utn.tpFinal.controller;

import com.utn.tpFinal.domain.Brand;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.exception.TariffExistException;
import com.utn.tpFinal.exception.TariffNotExistException;
import com.utn.tpFinal.service.BrandService;
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
@RequestMapping("/Brand")
public class BrandController {

    private BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity addBrand(@RequestBody Brand brand)
    {
        Brand b = brandService.addBrand(brand);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{brandId}")
                .buildAndExpand("Brand/"+b.getBrandId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "{brandId}", produces = "application/json")
    public ResponseEntity<Brand> getBrandById(@PathVariable("brandId") Integer brandId)
    {
       Brand brand = brandService.getBrandById(brandId);
        return ResponseEntity.ok(brand);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Brand>> getAllBrand(Pageable pageable) {
        Page page = brandService.getAll(pageable);
        return response(page);
    }

    @PutMapping(value = "/{branId}/Model/{modelId}", produces = "application/json")
    public void addModelToBrand(@PathVariable Integer  branId, @PathVariable Integer modelId) {
        brandService.addModelInBrand(branId,modelId);
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
