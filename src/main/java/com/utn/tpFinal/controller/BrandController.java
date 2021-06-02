package com.utn.tpFinal.controller;

import com.utn.tpFinal.domain.Brand;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public PostResponse addBrand(@RequestBody Brand newBrand){
        return brandService.save(newBrand);
    }
    @GetMapping
    public List<Brand> getAll(){
        return brandService.getAll();
    }

    @GetMapping("/{brandId}")
    public Brand betBrandById(@PathVariable Integer brandId){
        return brandService.getBrandById(brandId);
    }

    @DeleteMapping("/{brandId}")
    public void deleteBybrandId(@PathVariable Integer branId){
        brandService.deleteByBrandId(branId);
    }

    @PutMapping("/{branId}/Model/{modelId}")
    public void addModelToBrand(@PathVariable Integer  branId, @PathVariable Integer modelId) {
        brandService.addModelInBrand(branId,modelId);
    }



}
