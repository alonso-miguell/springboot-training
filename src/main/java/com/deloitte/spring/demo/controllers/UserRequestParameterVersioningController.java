package com.deloitte.spring.demo.controllers;


import java.util.Optional;

import javax.validation.constraints.Min;

import com.deloitte.spring.demo.dtos.UserDtoV1;
import com.deloitte.spring.demo.dtos.UserDtoV2;
import com.deloitte.spring.demo.entities.User;
import com.deloitte.spring.demo.exceptions.UserNotFoundException;
import com.deloitte.spring.demo.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/versioning/params/users")
public class UserRequestParameterVersioningController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    //Request Parameter based Versioning -  V1
    @GetMapping(value = "/{id}", params = "version=1")
    public UserDtoV1 getUserById(@PathVariable("id") @Min(1) Long id) throws UserNotFoundException {

        Optional<User> userOptional = userService.getUserById(id);

        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("user not found");
        }

        User user = userOptional.get();

        UserDtoV1 userDtoV1 = modelMapper.map(user, UserDtoV1.class);
        return userDtoV1;

    }


    //Request Paramter based Versioning -  V2
    @GetMapping(value = "/{id}", params = "version=2")
    public UserDtoV2 getUserById2(@PathVariable("id") @Min(1) Long id) throws UserNotFoundException {

        Optional<User> userOptional = userService.getUserById(id);

        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("user not found");
        }

        User user = userOptional.get();

        UserDtoV2 userDtoV2 = modelMapper.map(user, UserDtoV2.class);
        return userDtoV2;

    }

}