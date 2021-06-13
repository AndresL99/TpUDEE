package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.User;
import com.utn.tpFinal.domain.dto.UserDTO;
import com.utn.tpFinal.exception.InvalidUserException;
import com.utn.tpFinal.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class UserService
{
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    /*public User login(String username, String password)
    {
        return userRepository.findByUserNameAndAndPassword(username, password);
    }*/

    public Page<User>getAll(Pageable pageable)
    {
        return userRepository.findAll(pageable);
    }


    public UserDTO login(String username, String password) throws InvalidUserException {
        User user = userRepository.findByUserNameAndAndPassword(username, password);

        if(user != null)
        {
            return modelMapper.map(user, UserDTO.class);
        }
        else
        {
            throw new InvalidUserException();
        }
    }




}
