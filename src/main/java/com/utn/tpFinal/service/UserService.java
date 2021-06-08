package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.User;
import com.utn.tpFinal.repository.UserRepository;
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

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password)
    {
        return userRepository.findByUserNameAndAndPassword(username, password);
    }

    public Page<User>getAll(Pageable pageable)
    {
        return userRepository.findAll(pageable);
    }







}
