package com.homemaker.Accounts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController{
    @GetMapping("/user-list")
    public List<String> getUserController(){
        List<String> userList = new ArrayList<>();
        userList.add("Data");
        return userList;
}

public static void main(String [] args ){
   }

}