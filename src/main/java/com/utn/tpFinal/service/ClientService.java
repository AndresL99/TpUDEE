package com.utn.tpFinal.service;


import com.utn.tpFinal.domain.Client;
import com.utn.tpFinal.domain.PostResponse;
import com.utn.tpFinal.domain.Tariff;
import com.utn.tpFinal.domain.User;
import com.utn.tpFinal.exception.ClientExistException;
import com.utn.tpFinal.exception.ClientNotExistException;
import com.utn.tpFinal.exception.TariffExistException;
import com.utn.tpFinal.exception.TariffNotExistException;
import com.utn.tpFinal.repository.ClientRepository;
import com.utn.tpFinal.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Client addClient(Client newClient)throws ClientExistException
    {
        if (!clientRepository.existsById(newClient.getUserId()))
        {
            return clientRepository.save(newClient);
        }
        else
        {
            throw new ClientExistException();
        }
    }

    public Client getClientById(Integer clientId)throws ClientNotExistException
    {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public Page<Client> getAllClient(Pageable pageable)
    {
        return clientRepository.findAll(pageable);
    }

    public void deleteById(Integer idUser) {
         clientRepository.deleteById(idUser);
    }

}
