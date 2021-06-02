package com.utn.tpFinal.controller;

import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.domain.dto.TariffDto;
import com.utn.tpFinal.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/Tariff")
public class TariffController {

    private TariffService tariffService;


    private ConversionService conversionService;

    @Autowired
    public TariffController(TariffService tariffService, ConversionService conversionService) {
        this.tariffService = tariffService;
        this.conversionService = conversionService;
    }

    @PostMapping
    public PostResponse addTariff(@RequestBody Tariff newTariff){
        return tariffService.addTariff(newTariff);
    }

    @GetMapping("/{idTariff}")
    public TariffDto getTariffById(@PathVariable Integer idTariff){

        return  conversionService.convert(tariffService.getTariffById(idTariff), TariffDto.class);
    }



}
