package com.ykeshtdar.StartP9Monolothic.service;

import com.ykeshtdar.StartP9Monolothic.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Value("${service.url.patientAuthorizationBase}")
    private String findUsernameUrlBase;
    private final RestTemplate restTemplate;

    public CustomUserDetailService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        LoginForm loginForm = loginFormRepository.findByUsername(username)
        String baseUrl = "http://localhost:8082/login/findUsername";
        String loadUserByUsernameUrl = String.format("%s/findUsername",findUsernameUrlBase);
        String url = UriComponentsBuilder.fromHttpUrl(loadUserByUsernameUrl)
                .queryParam("username",username)
                .toUriString();

        LoginForm loginForm = restTemplate.getForObject(url,LoginForm.class);


        UserDetails userDetails = User.builder()
                .username(loginForm.getUsername())
                .password(loginForm.getPassword())
                .build();
        return userDetails;
    }
}
