package com.training.spring.demo.controllers;

import java.util.List;
import java.util.Optional;

import com.training.spring.demo.entities.User;
import com.training.spring.demo.exceptions.UserExistsException;
import com.training.spring.demo.exceptions.UserNameNotFoundException;
import com.training.spring.demo.exceptions.UserNotFoundException;
import com.training.spring.demo.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@Validated
@RequestMapping(value = "/users")
@Api(tags = "User Management RESTful Services", value = "UserController", description = "Controller for User Management Service")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @ApiOperation(value = "Retrieve list of users")
    public List<User> getAllUsers() {

        return userService.getAllUsers();

    }

    @PostMapping
    @ApiOperation(value = "Creates a new user")
    public ResponseEntity<Void> createUser(@Valid @RequestBody User user, UriComponentsBuilder builder) {
        try {
            userService.createUser(user);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

        } catch(UserExistsException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") @Min(1) Long id) {

        try {
            Optional<User> userOptional =  userService.getUserById(id);
            return userOptional.get();
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }

    @PutMapping("/{id}")
    public User updateUserById(@PathVariable("id") Long id, @RequestBody User user) {

        try {
            return userService.updateUserById(id, user);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/byusername/{username}")
    public User getUserByUsername(@PathVariable("username") String username) throws UserNameNotFoundException {
        User user =  userService.getUserByUsername(username);
        if(user == null)
            throw new UserNameNotFoundException("Username: '" + username + "' not found in User repository");
        return user;

    }

}