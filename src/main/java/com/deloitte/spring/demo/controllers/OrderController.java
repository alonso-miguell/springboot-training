package com.deloitte.spring.demo.controllers;

import java.util.List;
import java.util.Optional;

import com.deloitte.spring.demo.entities.Order;
import com.deloitte.spring.demo.entities.User;
import com.deloitte.spring.demo.exceptions.UserNotFoundException;
import com.deloitte.spring.demo.repositories.OrderRepository;
import com.deloitte.spring.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/users")
public class OrderController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/{userid}/orders")
    public List<Order> getAllOrders(@PathVariable Long userid) throws UserNotFoundException {

        Optional<User> userOptional = userRepository.findById(userid);
        if (!userOptional.isPresent())
            throw new UserNotFoundException("User Not Found");

        return userOptional.get().getOrders();
    }

    @GetMapping("/orders/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) throws UserNotFoundException {

        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (!orderOptional.isPresent())
            throw new UserNotFoundException("Order Not Found");

        return orderOptional.get();
    }

    @PostMapping("{userid}/orders")
    public Order createOrder(@PathVariable Long userid, @RequestBody Order order) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userid);

        if (!userOptional.isPresent())
            throw new UserNotFoundException("User Not Found");

        User user = userOptional.get();
        order.setUser(user);
        return orderRepository.save(order);

    }

}