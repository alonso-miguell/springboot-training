package com.deloitte.spring.demo.controllers;

import java.util.List;
import java.util.Optional;

import com.deloitte.spring.demo.entities.Order;
import com.deloitte.spring.demo.entities.User;
import com.deloitte.spring.demo.exceptions.UserNotFoundException;
import com.deloitte.spring.demo.repositories.OrderRepository;
import com.deloitte.spring.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/hateoas/users")
public class OrderHateoasController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    // get All Orders for a user

    @GetMapping("/{userid}/orders")
    public CollectionModel<Order> getAllOrders(@PathVariable Long userid) throws UserNotFoundException {

        Optional<User> userOptional = userRepository.findById(userid);
        if (!userOptional.isPresent())
            throw new UserNotFoundException("User Not Found");

        List<Order> allorders =  userOptional.get().getOrders();
        CollectionModel<Order> finalResources = CollectionModel.of(allorders);

        return finalResources;
    }
}