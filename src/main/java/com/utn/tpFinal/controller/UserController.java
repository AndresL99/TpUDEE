package com.utn.tpFinal.controller;

import com.utn.tpFinal.domain.User;
import com.utn.tpFinal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController
{
    @Autowired
    private UserService userService;


    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }
    @GetMapping("/{userName}")
    public User getUserByName(@PathVariable String userName){
        return userService.getByUserName(userName);
    }

    @DeleteMapping("/{username}")
    void deletUserByUserName(@PathVariable String userName){
        userService.deleteUser(userName);
    }



}
