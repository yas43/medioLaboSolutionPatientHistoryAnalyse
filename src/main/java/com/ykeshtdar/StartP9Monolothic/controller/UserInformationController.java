package com.ykeshtdar.StartP9Monolothic.controller;

import com.ykeshtdar.StartP9Monolothic.model.*;
import com.ykeshtdar.StartP9Monolothic.service.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserInformationController {
    private final UserInformationService userInformationService;

    public UserInformationController(UserInformationService userInformationService) {
        this.userInformationService = userInformationService;
    }

    @GetMapping
    public UserInformation displayUserByName(@RequestParam("name")String name){
        return userInformationService.findByUsername(name);
    }

    @PostMapping
    public UserInformation addUser(@RequestBody UserInformation userInformation){
         return userInformationService.addUserInformation(userInformation);
    }
}
