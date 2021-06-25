package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.*;
import com.utn.tpFinal.domain.dto.UserDTO;
import com.utn.tpFinal.domain.proyection.Top10MoreConsumption;
import com.utn.tpFinal.exception.*;
import com.utn.tpFinal.repository.ClientRepository;
import com.utn.tpFinal.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClientService {

    private ClientRepository clientRepository;
    private UserRepository userRepository;
    private static final String CLIENT_PATH ="Client";
    private ModelMapper modelMapper;

    @Autowired
    public ClientService(ClientRepository clientRepository, ModelMapper modelMapper,UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
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


    public UserDTO login(String username, String password) throws InvalidUserException {
        Client client = (Client) userRepository.findByUserNameAndAndPassword(username, password);

        if(client != null)
        {
            return modelMapper.map(client,UserDTO.class);
        }
        else
        {
            throw new InvalidUserException();
        }
    }

    public List<Top10MoreConsumption> getTop10MoreConsumtion(LocalDateTime from, LocalDateTime to) {

        return clientRepository.getTop10(from,to);
    }
}
