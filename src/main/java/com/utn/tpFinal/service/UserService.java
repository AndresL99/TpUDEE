package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.User;
import com.utn.tpFinal.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User>getAll(Pageable pageable)
    {
        return userRepository.findAll(pageable);
    }


    public User getUsernameAndPassword(String username,String password)
    {
        return userRepository.findByUsernameAndPassword(username,password);
    }

}
