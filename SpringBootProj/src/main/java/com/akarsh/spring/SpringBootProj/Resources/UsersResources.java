package com.akarsh.spring.SpringBootProj.Resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.akarsh.spring.SpringBootProj.Models.Users;
import com.akarsh.spring.SpringBootProj.Repositories.UsersRepo;


import java.util.List;
@RestController
@RequestMapping(value = "/rest/users")

public class UsersResources {

    @Autowired
    UsersRepo usersRepository;

    @GetMapping(value = "/all")
    public List<Users> getAll() {
        return usersRepository.findAll();
    }

    @PostMapping(value = "/load")
    public List<Users> persist(@RequestBody final Users users) {
        usersRepository.save(users);
        return usersRepository.findAll();
    }


}
