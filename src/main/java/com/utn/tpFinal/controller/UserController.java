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

    @PostMapping
    public void addUser(@RequestBody User newUser){
        userService.addUser(newUser);
    }


    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }
    @GetMapping("/{id_user}")
    public User getUserById(@PathVariable Integer idUser){
        return userService.getByUserById(idUser);
    }

    @DeleteMapping("/{id_user}")
    void deleteUserById(@PathVariable Integer idUser){
        userService.deleteUser(idUser);
    }


    @GetMapping("/{userName}")
    public User getUserByUserName(@PathVariable String userName){
        return userService.getUserByUserName(userName);
    }

    @DeleteMapping("/{userName}")
    void deleteUserByUserName(@PathVariable String userName){
        userService.deleteByUserName(userName);
    }



}
