package com.utn.tpFinal.controller;

import com.utn.tpFinal.domain.Client;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.domain.User;
import com.utn.tpFinal.domain.dto.ClientDto;
import com.utn.tpFinal.exception.ClientExistException;
import com.utn.tpFinal.exception.ClientNotExistException;
import com.utn.tpFinal.exception.TariffExistException;
import com.utn.tpFinal.exception.TariffNotExistException;
import com.utn.tpFinal.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
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
@RequestMapping("/Client")
public class ClientController {

    private ClientService clientService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity addClient(@RequestBody Client client) throws ClientExistException
    {
        Client c = clientService.addClient(client);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{clientId}")
                .buildAndExpand("Client/"+c.getClientId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/{clientId}", produces = "application/json")
    public ResponseEntity<Client> getClientById(@PathVariable("clientId") Integer clientId) throws ClientNotExistException
    {
        Client client = clientService.getClientById(clientId);
        return ResponseEntity.ok(client);
    }

    @GetMapping(produces = "application/json", value = "/")
    public ResponseEntity<List<Client>> getAllClient(Pageable pageable) {
        Page page = clientService.getAllClient(pageable);
        return response(page);
    }

    @DeleteMapping(value = "/{id_user}", produces = "application/json")
    void deleteClientByUserName(@PathVariable Integer clientId){
        clientService.deleteById(clientId);
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
