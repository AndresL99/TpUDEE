package com.utn.tpFinal.service;


import com.utn.tpFinal.domain.Client;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.domain.User;
import com.utn.tpFinal.repository.ClientRepository;
import com.utn.tpFinal.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class ClientService {

    private ClientRepository clientRepository;
    private static final String CLIENT_PATH ="Client";

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public PostResponse add(Client newClient) {
        Client c = clientRepository.save(newClient);
        return PostResponse
                .builder()
                .status(HttpStatus.CREATED)
                .url(EntityURLBuilder.buildURL(CLIENT_PATH,c.getUserId().toString()))
                .build();
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public void deleteById(Integer idUser) {
         clientRepository.deleteById(idUser);
    }

    public Client getByClientById(Integer idUser) {
        return clientRepository.findById(idUser)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }
}
