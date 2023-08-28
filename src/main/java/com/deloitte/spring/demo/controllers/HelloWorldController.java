package com.deloitte.spring.demo.controllers;


import com.deloitte.spring.demo.pojos.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/helloworld1")
    public String helloWorld() {
        return "Hello World1";
    }

    @GetMapping("/helloworld-bean")
    public UserDetails helloWorldBean() {
        return new UserDetails("Miguel", "Alonso", "CDMX");
    }

}