package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.Client;
import com.utn.tpFinal.domain.User;
import com.utn.tpFinal.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void add(Client newClient) {
        clientRepository.save(newClient);
    }


    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public void deleteByUserName(String userName) {
         clientRepository.deleteById(userName);
    }


    public Client getByClientName(String userName) {
        return clientRepository.findById(userName)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }
}
