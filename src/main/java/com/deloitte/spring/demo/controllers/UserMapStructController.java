package com.deloitte.spring.demo.controllers;


import java.util.List;
import java.util.Optional;

import com.deloitte.spring.demo.dtos.UserMapStructDto;
import com.deloitte.spring.demo.entities.User;
import com.deloitte.spring.demo.mappers.UserMapperStruct;
import com.deloitte.spring.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/mapstruct/users")
public class UserMapStructController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapperStruct userMapper;

    @GetMapping
    public List<UserMapStructDto> getAllUserDtos() {
        return userMapper.usersToUserDtos(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public UserMapStructDto getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.get();
        return userMapper.userToUserMsDto(user);
    }

}