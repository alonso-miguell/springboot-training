package com.deloitte.spring.demo.controllers;

import java.util.Optional;

import javax.validation.constraints.Min;

import com.deloitte.spring.demo.entities.User;
import com.deloitte.spring.demo.entities.Views;
import com.deloitte.spring.demo.exceptions.UserNotFoundException;
import com.deloitte.spring.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;
@RestController
@Validated
@RequestMapping(value = "/jsonview/users")
public class UserJsonViewController {


    @Autowired
    private UserService userService;

    @JsonView(Views.External.class)
    @GetMapping("/external/{id}")
    public Optional<User> getExternalView(@PathVariable("id") @Min(1) Long id) {

        try {
            return userService.getUserById(id);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }

    @GetMapping("/internal/{id}")
    @JsonView(Views.Internal.class)
    public Optional<User> getInternalView(@PathVariable("id") @Min(1) Long id) {

        try {
            return userService.getUserById(id);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }


}