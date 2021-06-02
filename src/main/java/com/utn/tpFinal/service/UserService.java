package com.utn.tpFinal.service;

import com.utn.tpFinal.domain.User;
import com.utn.tpFinal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void addUser(User newUser){
        userRepository.save(newUser);
    }

    public List<User> getAll(){
       return userRepository.findAll();
    }

    public User getByUserById(Integer idUser) {
        return userRepository.findById(idUser)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public void deleteUser(Integer idUser)
    {
        userRepository.deleteById(idUser);
    }

    public User getUserByUserName(String userName)
    {
        return userRepository.findByUserName(userName);
    }

    public void deleteByUserName(String userName)
    {
        userRepository.deleteByUserName(userName);
    }





}
