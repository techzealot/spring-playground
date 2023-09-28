package com.techzealot.spring.playground.orm.controller;


import com.techzealot.spring.playground.orm.dto.AppUserDto;
import com.techzealot.spring.playground.orm.jpa.repository.AppUserRepository;
import com.techzealot.spring.playground.orm.mapper.AppUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppUserController {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppUserMapper appUserMapper;

    @GetMapping("/user/{id}")
    public AppUserDto findById(@PathVariable long id) {
        return appUserRepository.findById(id).map(appUserMapper::toDto).orElse(null);
    }
}
