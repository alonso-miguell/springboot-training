package com.deloitte.spring.demo.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;

import com.deloitte.spring.demo.entities.Order;
import com.deloitte.spring.demo.entities.User;
import com.deloitte.spring.demo.exceptions.UserNotFoundException;
import com.deloitte.spring.demo.repositories.UserRepository;
import com.deloitte.spring.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/hateoas/users")
@Validated
public class UserHateoasController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public EntityModel<User> getUserById(@PathVariable("id") @Min(1) Long id) {

        try {
            Optional<User> userOptional =  userService.getUserById(id);
            User user = userOptional.get();
            Long userid = user.getId();
            Link selflink = linkTo(this.getClass()).slash(userid).withSelfRel();
            user.add(selflink);

            EntityModel<User> entityModel= EntityModel.of(user);
            return  entityModel;
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }

    @GetMapping
    public CollectionModel<User> getAllUsers() throws UserNotFoundException {
        List<User> allusers = userService.getAllUsers();

        for(User user : allusers) {
            //Self Link
            Long userid = user.getId();
            Link selflink = linkTo(this.getClass()).slash(userid).withSelfRel();
            user.add(selflink);

            //Relationship link with getAllOrders
            CollectionModel<Order> orders = methodOn(OrderHateoasController.class).getAllOrders(userid);
            Link orderslink = linkTo(orders).withRel("all-orders");
            user.add(orderslink);
        }

        //Self link for getAllUsers
        Link selflinkgetAllUsers = linkTo(this.getClass()).withSelfRel();
        CollectionModel<User> finalResources = CollectionModel.of(allusers, selflinkgetAllUsers);
        return finalResources;
    }

}