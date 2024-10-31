package com.ykeshtdar.StartP9Monolothic.controller;

import com.ykeshtdar.StartP9Monolothic.model.*;
import com.ykeshtdar.StartP9Monolothic.service.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserInformationController {
    private final UserInformationService userInformationService;

    public UserInformationController(UserInformationService userInformationService) {
        this.userInformationService = userInformationService;
    }

    @GetMapping
    public UserInformation displayUserByName(@RequestParam("firstname")String name){
        return userInformationService.findByUsername(name);
    }

    @GetMapping("/all")
    public List<UserInformation> displayAllUser(){
        return userInformationService.displayAllUserInformation();
    }

    @PostMapping
    public UserInformation addUser(@RequestBody UserInformation userInformation){
         return userInformationService.addUserInformation(userInformation);
    }

    @PutMapping("/update")
    public UserInformation update(@RequestParam("firstname")String firstname,
                                  @RequestParam("lastname")String lastname,
                                  @RequestParam("gender")String gender,
                                  @RequestParam("birthdate") LocalDate birthdate,
                                  @RequestParam("address")String address,
                                  @RequestParam("phoneNumber")String phoneNumber
                                  ){

        return userInformationService.updateUserInformation(firstname,lastname,gender,birthdate,address,phoneNumber);
    }

    @DeleteMapping
    public void deleteUser(@RequestParam("firstname")String firstname){
         userInformationService.deleteUser(firstname);
    }
}
