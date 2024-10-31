package com.ykeshtdar.StartP9Monolothic.service;

import com.ykeshtdar.StartP9Monolothic.model.*;
import com.ykeshtdar.StartP9Monolothic.repository.*;
import org.springframework.stereotype.*;

import java.time.LocalDate;
import java.util.*;

@Service
public class UserInformationService {
    private final UserInformationRepository userInformationRepository;

    public UserInformationService(UserInformationRepository userInformationRepository) {
        this.userInformationRepository = userInformationRepository;
    }

    public UserInformation addUserInformation(UserInformation userinformation) {
        if (isAlreadyExist(userinformation.getFirstname())){
            throw new RuntimeException("this user is already exist");
        }
       return userInformationRepository.save(userinformation);
    }

    public UserInformation findByUsername(String name) {
        return userInformationRepository.findByFirstname(name)
                .orElseThrow(()->new RuntimeException("can not find user by this username"));
    }

    public UserInformation updateUserInformation(String firstname,
                                                 String lastname,
                                                 String gender,
                                                 LocalDate birthdate,
                                                 String address,
                                                 String phoneNumber) {
        if (!isAlreadyExist(firstname)){
             throw  new RuntimeException("this user dose not exist");
        }
       UserInformation userInformation = findByUsername(firstname);
        userInformation.setFirstname(firstname);
        userInformation.setLastname(lastname);
        userInformation.setGender(gender);
        userInformation.setBirthdate(birthdate);//format:uuuu-MM-d
        userInformation.setAddress(address);
        userInformation.setPhoneNumber(phoneNumber);
        return userInformationRepository.save(userInformation);
    }

    public List<UserInformation> displayAllUserInformation() {
        return userInformationRepository.findAll();
    }

    public void deleteUser(String firstname) {
        UserInformation userInformation = findByUsername(firstname);
        userInformationRepository.delete(userInformation);
    }


    public Boolean isAlreadyExist(String firstname){
       return userInformationRepository.existsByFirstname(firstname);
    }
}
