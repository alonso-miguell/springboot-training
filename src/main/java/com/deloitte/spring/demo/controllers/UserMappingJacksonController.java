package com.deloitte.spring.demo.controllers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.Min;

import com.deloitte.spring.demo.entities.User;
import com.deloitte.spring.demo.exceptions.UserNotFoundException;
import com.deloitte.spring.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping(value = "/jacksonfilter/users")
@Validated
public class UserMappingJacksonController {

    @Autowired
    private UserService userService;

    //Predefined fields to be shown
    @GetMapping("/{id}")
    public MappingJacksonValue getUserById(@PathVariable("id") @Min(1) Long id) {

        try {

            Optional<User> userOptional = userService.getUserById(id);
            User user = userOptional.get();

            //Filter will show only specified fields
            Set<String> fields = new HashSet<String>();
            fields.add("id");
            fields.add("username");
            fields.add("ssn");
            fields.add("orders");

            FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter",
                    SimpleBeanPropertyFilter.filterOutAllExcept(fields));
            MappingJacksonValue mapper = new MappingJacksonValue(user);

            mapper.setFilters(filterProvider);
            return mapper;

        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }

    // Dynamic filtering by sending wanted fields in fields param
    @GetMapping("/params/{id}")
    public MappingJacksonValue getUserById2(@PathVariable("id") @Min(1) Long id,
                                            @RequestParam Set<String> fields) {

        try {

            Optional<User> userOptional = userService.getUserById(id);
            User user = userOptional.get();

            FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter",
                    SimpleBeanPropertyFilter.filterOutAllExcept(fields));
            MappingJacksonValue mapper = new MappingJacksonValue(user);

            mapper.setFilters(filterProvider);
            return mapper;

        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }

}
