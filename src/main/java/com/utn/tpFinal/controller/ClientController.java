package com.utn.tpFinal.controller;

import com.utn.tpFinal.domain.Client;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.domain.User;
import com.utn.tpFinal.domain.dto.ClientDto;
import com.utn.tpFinal.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/Client")
public class ClientController {

    private ClientService clientService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    PostResponse addClient(@RequestBody Client newClient){
       return clientService.add(newClient);
    }

    @GetMapping("/{userName}")
    public ClientDto getClientByName(@PathVariable String userName){

        return conversionService.convert(clientService.getByClientName(userName), ClientDto.class);
    }

    @GetMapping
    public List<Client> getAll(){
        return clientService.getAll();
    }

    @DeleteMapping("/{userName}")
    void deleteClientByUserName(@PathVariable String userName){
        clientService.deleteByUserName(userName);
    }

}
