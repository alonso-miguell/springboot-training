package com.training.spring.demo.controllers;

import java.util.Optional;

import javax.validation.constraints.Min;

import com.training.spring.demo.dtos.UserModelMapperDto;
import com.training.spring.demo.entities.User;
import com.training.spring.demo.exceptions.UserNotFoundException;
import com.training.spring.demo.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/modelmapper/users")
public class UserModelMapperController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public UserModelMapperDto getUserById(@PathVariable("id") @Min(1) Long id) throws UserNotFoundException {

        Optional<User> userOptional = userService.getUserById(id);

        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("user not found");
        }

        User user = userOptional.get();

        UserModelMapperDto userMmDto = modelMapper.map(user, UserModelMapperDto.class);
        return userMmDto;

    }
}
