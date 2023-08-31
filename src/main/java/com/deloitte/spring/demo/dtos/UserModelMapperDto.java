package com.deloitte.spring.demo.dtos;

import com.deloitte.spring.demo.entities.Order;

import java.util.List;

public class UserModelMapperDto {

    private Long id;
    private String username;
    private String firstname;
//    private List<Order> orders;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
//    public List<Order> getOrders() {
//        return orders;
//    }
//    public void setOrders(List<Order> orders) {
//        this.orders = orders;
//    }

}