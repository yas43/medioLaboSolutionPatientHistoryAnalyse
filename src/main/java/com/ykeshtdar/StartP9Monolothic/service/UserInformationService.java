package com.ykeshtdar.StartP9Monolothic.service;

import com.ykeshtdar.StartP9Monolothic.model.*;
import com.ykeshtdar.StartP9Monolothic.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class UserInformationService {
    private final UserInformationRepository userInformationRepository;

    public UserInformationService(UserInformationRepository userInformationRepository) {
        this.userInformationRepository = userInformationRepository;
    }

    public UserInformation addUserInformation(UserInformation userinformation) {
       return userInformationRepository.save(userinformation);
    }

    public UserInformation findByUsername(String name) {
        return userInformationRepository.findByFirstname(name)
                .orElseThrow(()->new RuntimeException("can not find user by this username"));
    }
}
