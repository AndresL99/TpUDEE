package com.utn.tpFinal.controller;

import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/Tariff")
public class TariffController {

    private TariffService tariffService;

    @Autowired
    public TariffController(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @PostMapping
    public PostResponse addTariff(@RequestBody Tariff newTariff){
        return tariffService.addTariff(newTariff);
    }

    @GetMapping("/{idTariff}")
    public Tariff getTariffById(@PathVariable String idTariff){
        return  tariffService.getTariffById(idTariff);
    }

    @DeleteMapping("/{idTariff}")
    public void deleteAddressById(@PathVariable String idTariff){
        tariffService.deleteAddressById(idTariff);
    }
}
