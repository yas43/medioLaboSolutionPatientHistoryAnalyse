package com.ykeshtdar.StartP9Monolothic.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;

@Service
public class JwtService {
    private final RestTemplate restTemplate;
    //    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
//    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
//    private final long validityInMilliseconds = 1000*30;
    @Value("${service.url.patientAuthorizationBase}")
    private String patientAuthBase;

    public JwtService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


//    public String generateToken(Authentication authentication) {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        Date now = new Date();
//        Date expiry = new Date(now.getTime() + validityInMilliseconds);
//
//        return Jwts.builder()
//                .setSubject(format("%s",userDetails.getUsername()))
//                .setIssuedAt(now)
//                .setExpiration(expiry)
//                .signWith(SignatureAlgorithm.HS256, SECRET)
//                .compact();
//    }




//    public String getUsername(String token) {
//
//        try {
//            String username = Jwts.parser()
//                    .setSigningKey(SECRET)
//                    .parseClaimsJws(token)
//                    .getBody()
//                    .getSubject();
//            return username;
//        } catch (Exception e) {
//            throw new IllegalArgumentException("username did not extracted", e);
//        }
//
//    }

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



//        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
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






//        try {
//            parser().setSigningKey(SECRET).parseClaimsJws(token);
//            System.out.println("token is valid");
//            return true;
//        } catch (JwtException | IllegalArgumentException e) {
//            System.out.println("token is not valid");
//            return false;
//        }
    }

}


