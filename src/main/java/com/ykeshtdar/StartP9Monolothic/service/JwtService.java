package com.ykeshtdar.StartP9Monolothic.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;

@Service
public class JwtService {
    private final RestTemplate restTemplate;
    @Value("${service.url.patientAuthorizationBase}")
    private String patientAuthBase;

    public JwtService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public String getUsername(String token) {


        System.out.println("inside patient jwt service");
        String getUsernameUrl = String.format("%s/getUsernameByToken",patientAuthBase);
        String url = UriComponentsBuilder.fromHttpUrl(getUsernameUrl)
                .queryParam("token",token)
                .toUriString();

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
            String username = response.getBody();
            System.out.println("inside patient jwt service and username is "+username);
            return username;
        }catch (Exception e){
            System.out.println("inside patient jwt service user name can not extracted");
            return "errorr occurred" +e.getMessage();
        }

    }



    public boolean validateToken(String token) {
        System.out.println("inside jwt service in front end");
        String validationUrl = String.format("%s/validate",patientAuthBase);
        String url = UriComponentsBuilder.fromHttpUrl(validationUrl)
                .queryParam("token",token)
                .toUriString();

        ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);


        if (response.getBody().equals("Valid")) {
            System.out.println("inside jwt service in front end token is valid");
            return true;
        }else {
            System.out.println("inside jwt service in front end token is not valid");
            return false;}

    }

}


